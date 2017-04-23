package eetac.dsa.control1; /**
 * Created by Home on 19/04/2017.
 */

import eetac.dsa.control1.entity.Uobject;
import eetac.dsa.control1.entity.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public abstract class EetakemonManagerDB <E> {

    protected static String capitalizeName (String name) {
        String capitalizedFieldName;
        capitalizedFieldName = name.substring(0,1).toUpperCase() + name.substring(1);
        return capitalizedFieldName;
    }

    protected static String getSetterName(String fieldName) {
        StringBuilder setterName = new StringBuilder("set");
        setterName.append(capitalizeName(fieldName));
        return setterName.toString();
    }

    public static void setStringField (String resultString, String name, Object object) {
        Method method;
        try {
            method = object.getClass().getMethod(getSetterName(name), resultString.getClass());
            method.invoke(object, resultString);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    protected static void setIntField (int resultId, String name, Object object) {
        Method method;
        try {
            Class[] arguments = new Class[1];
            arguments[0] = Integer.class;
            method = object.getClass().getMethod(getSetterName(name), arguments);
            method.invoke(object, resultId);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void setFieldsFromResultSet(ResultSet resultSet, ResultSetMetaData resultSetMetaData, Object object) {
        try {
            for(int i=1; i <= resultSetMetaData.getColumnCount(); i++) {
                String columnType = resultSetMetaData.getColumnTypeName(i);
                String columnName = resultSetMetaData.getColumnLabel(i);
                switch(columnType) {
                    case "VARCHAR":
                        String resultString = resultSet.getString(i);
                        setStringField(resultString, columnName, object);
                        break;
                    case "INT":
                        int resultInt = resultSet.getInt(i);
                        setIntField(resultInt, columnName, object);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection createConnection() {
        Connection con = null;
        try {
            ResourceBundle data = ResourceBundle.getBundle("config.cfg");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(data.getString("URL"),data.getString("USER"),data.getString("PASS"));
            System.out.println("******* Connection created Successfully........");
        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
        }
        return con;
    }

    private static void closeConnection(Connection con){
        try{
            con.close();
            System.out.println("******* Connection closed Successfully.........");
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

    protected void insert() throws Exception {
        StringBuffer buffer = new StringBuffer("INSERT INTO " + this.getClass().getSimpleName().toLowerCase() + "(");
        StringBuffer buffer2 = new StringBuffer("VALUES (");
        Field[] atributos = this.getClass().getDeclaredFields();
        Method[] metodos = this.getClass().getMethods();
        Object result;

        int i;
        for (i = 0; i < atributos.length - 1; i++) {
            buffer.append(atributos[i].getName() + ",");
            buffer2.append("?,");
        }
        buffer.append(atributos[i].getName() + ") ");
        buffer2.append("?)");
        buffer.append(buffer2);
        Connection con = createConnection();
        System.out.println(buffer);
        PreparedStatement preparedStatement = con.prepareStatement(buffer.toString(),PreparedStatement.RETURN_GENERATED_KEYS);
        i = 0;
        for (Field field : atributos) {
            boolean found=false;
            for (Method metodo : metodos) {
                String s = metodo.getName().toLowerCase();
                if (s.startsWith("get") && s.contains(atributos[i].getName())) {
                    result = metodo.invoke(this, null);
                    i++;
                    preparedStatement.setObject(i, result);
                    found=true;
                }
                if(found) break;
            }
        }
        preparedStatement.executeUpdate();
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            setIntField(generatedKeys.getInt(1), "id", this);
        }
        preparedStatement.close();
        closeConnection(con);
    }

    protected Object select() throws Exception {
        StringBuffer buffer = new StringBuffer("SELECT * FROM " + this.getClass().getSimpleName().toLowerCase() + " WHERE ");
        buffer.append(this.getPrimaryKey()).append("=?");
        Connection con=createConnection();
        PreparedStatement preparedStatement = con.prepareStatement(buffer.toString());
        preparedStatement.setObject(1,this.getClass().getMethod(this.getPKMethod()).invoke(this,null));
        ResultSet resultado = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultado.getMetaData();
        List<Object> list = result(resultado,resultSetMetaData);
        preparedStatement.close();
        closeConnection(con);
        return list.get(0);
    }

    protected List<Object> findAll() throws Exception {
        StringBuffer buffer = new StringBuffer("SELECT * FROM " + this.getClass().getSimpleName().toLowerCase());
        Connection con=createConnection();
        PreparedStatement preparedStatement = con.prepareStatement(buffer.toString());
        ResultSet resultado = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultado.getMetaData();
        List<Object> lista = result(resultado,resultSetMetaData);
        preparedStatement.close();
        closeConnection(con);
        return lista;
    }

    public List<Object> selectObjects(Integer id) throws Exception {
        StringBuffer buffer = new StringBuffer("SELECT uobject.name, description FROM uobject, user WHERE uobject.user = user.id AND user.id=?");
        Connection con=createConnection();
        PreparedStatement preparedStatement = con.prepareStatement(buffer.toString());
        preparedStatement.setInt(1,id);
        ResultSet resultado = preparedStatement.executeQuery();
        ResultSetMetaData resultSetMetaData = resultado.getMetaData();
        List<Object> lista = result(resultado,resultSetMetaData);
        preparedStatement.close();
        closeConnection(con);
        return lista;
    }

    public void addobject(Integer id) throws Exception {
        StringBuffer buffer = new StringBuffer("INSERT INTO uobject(name,description,user) VALUES(?,?,?)");
        Connection con=createConnection();
        PreparedStatement preparedStatement = con.prepareStatement(buffer.toString());
        preparedStatement.setObject(1,this.getClass().getMethod("getName").invoke(this,null));
        preparedStatement.setObject(2,this.getClass().getMethod("getDescription").invoke(this,null));
        preparedStatement.setInt(3,id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        closeConnection(con);
    }

    protected void update() throws Exception {
        StringBuffer buffer = new StringBuffer("UPDATE " + this.getClass().getSimpleName().toLowerCase() + " SET ");
        StringBuffer buffer2 = new StringBuffer("WHERE ");
        Field[] atributos = this.getClass().getDeclaredFields();
        Method[] metodos = this.getClass().getMethods();
        Object result;

        int i;
        for (i = 0; i < atributos.length - 1; i++) {
            if(!atributos[i].getName().equals(this.getPrimaryKey())) {
                buffer.append(atributos[i].getName() + "=?,");
            }
            else{
                buffer2.append(atributos[i].getName() + "=?");
            }
        }
        buffer.append(atributos[i].getName() + "=? ");
        buffer.append(buffer2);

        PreparedStatement prest;
        Connection con = createConnection();
        prest = con.prepareStatement(buffer.toString());

        int PKNotFound = 1;
        for (i = 0; i < atributos.length; i++) {

            for (Method metodo:metodos) {
                String s = metodo.getName().toLowerCase();
                if(s.startsWith("get") && s.contains(atributos[i].getName())) {
                    result = metodo.invoke(this, null);
                    if(!atributos[i].getName().equals(this.getPrimaryKey())) {
                        if(result.getClass().getName() == "java.lang.String") prest.setString(i + PKNotFound, result.toString());
                        else prest.setInt(i + PKNotFound, (Integer)result);
                    }
                    else{
                        if(result.getClass().getName() == "java.lang.String") prest.setString(atributos.length, result.toString());
                        else prest.setInt(atributos.length, (Integer)result);
                        PKNotFound = 0;
                    }
                    break;
                }
            }
        }
        prest.executeUpdate();
        prest.close();
        closeConnection(con);
    }

    protected List<Object> result (ResultSet resultset, ResultSetMetaData resultsetmetadata) throws Exception{
        List<Object> lista = new ArrayList<Object>();
        while (resultset.next()) {
            Class classToLoad = this.getClass();
            Object newObject = classToLoad.newInstance();
            setFieldsFromResultSet(resultset, resultsetmetadata, newObject);
            lista.add(newObject);
        }
        return lista;
    }

    public abstract String getPrimaryKey();
    public abstract String getPKMethod();



}
