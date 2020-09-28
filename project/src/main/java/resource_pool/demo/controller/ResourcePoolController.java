package resource_pool.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import resource_pool.demo.connection.SqlConnection;
import resource_pool.demo.domain.Table_name;
import resource_pool.demo.domain.table_information;
import resource_pool.demo.mapper.RPMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import resource_pool.demo.connection.SqlConnection;


@RestController
@RequestMapping("/ResourcePool")
public class ResourcePoolController {

    private int InitConnection=5;

    private SqlConnection sqlconnection;

    @Autowired
    public RPMapper rpMapper;

    public ResourcePoolController() throws SQLException {
        this.sqlconnection=new SqlConnection(this.InitConnection);
    }

    @PostMapping("add_infor")
    public Object add_infor(@RequestBody LinkedHashMap user){
        int type=(Integer)user.get("type");
        if(type>=0&&type<=2){
            user.put("information","没有添加权限");
        }
        else{
            String title=user.get("title").toString();
            List content=(ArrayList)user.get("content");
            String sql="select *from table_form where title=\""+title+"\"";
            System.out.println(sql);
            List<table_information> list=rpMapper.search_table(sql);
            String table_name=list.get(0).getName();
            String infor="(";
            String name="(";
            sql="Insert into "+table_name;
            for(int i=0;i<content.size();i++){
                LinkedHashMap data=(LinkedHashMap)content.get(i);

                if(i==0){
                    infor+=data.get("infor");
                    name+= "\"" + data.get("name")+"\"";
                }
                else {
                    infor += "," + data.get("infor");
                    name += ",\"" + data.get("name")+"\"";
                }
            }
            if(infor.equals("(")){
                user.put("information","no input");
                return user;
            }
            infor+=")";
            name+=")";
            sql+=" "+infor+" values "+name;
            System.out.println(sql);
            rpMapper.insert(sql);
            user.put("information","success");
        }
        return user;
    }

    @PostMapping("create_database")
    public Object databases(@RequestBody LinkedHashMap user) {
        System.out.println(user.get("title"));
        String title=user.get("title").toString();
        System.out.println(user.get("content"));
        List list=(ArrayList)user.get("content");
        String sql = "create table " + title + "(";
        int num=0;
        for(int i=0;i<list.size();i++){
            LinkedHashMap data=(LinkedHashMap)list.get(i);
            String record=data.get("name").toString();
            String type=data.get("type").toString();
            if(num==0){
                sql+=record+" "+type;
                num++;
            }
            else sql+=","+record+" "+type;
        }
        sql += ");";
        System.out.println(sql);
        rpMapper.create(sql);
        sql="insert into table_form(name,title,del) values(\""+title+"\",\""+title+"资源池\",1)";
        System.out.println(sql);
        rpMapper.insert(sql);
        System.out.println("finished");

        user.put("information", "success");
        return user;
    }

    @PostMapping("take_tables")
    public Object taketables() {
        List<String> result=new ArrayList<>();
        List<table_information> list=rpMapper.take_table();
        for(int i=0;i<list.size();i++){
            String title=list.get(i).getTitle();
            result.add(title);
        }
        return result;
    }

    @PostMapping("findByGroup")
    public Object findByGroup(@RequestBody LinkedHashMap user) {
        HashMap result=new HashMap();
        List<String> records=new ArrayList<>();
        String title=user.get("title").toString();
        String record=user.get("record").toString();
        String sql="select *from table_form where title=\""+title+"\"";
        System.out.println(sql);
        List<table_information> list=rpMapper.search_table(sql);
        String table_name=list.get(0).getName();
        sql="select count(*) as value,"+record+" as name from "+table_name+" group by "+record+" limit 10";
        System.out.println(sql);
        List<HashMap<String,Object>> lists=rpMapper.find(sql);
        for(int i=0;i<lists.size();i++){
            if(lists.get(i).get("name")!=null)
                records.add(lists.get(i).get("name").toString());
            else records.add("其他");
        }
        result.put("records",records);
        result.put("values",lists);
        return result;
    }

    @PostMapping("delete_database")
    public Object delete_databases(@RequestBody LinkedHashMap user){
        if(!user.containsKey("title")) {
            user.put("information","没有删除权限");
            return user;
        }
        String title=user.get("title").toString();
        String sql="select *from table_form where title=\""+title+"\"";
        System.out.println(sql);
        List<table_information> list=rpMapper.search_table(sql);
        if(list.get(0).getDel()==1){
            sql="delete from table_form where title=\""+title+"\"";
            System.out.println(sql);
            rpMapper.delete(sql);
            sql="drop table "+list.get(0).getName();
            System.out.println(sql);
            rpMapper.delete(sql);
            user.put("information","删除成功，请刷新");
        }
        else user.put("information","没有删除权限");
        return user;
    }

    @PostMapping("findContent")
    public Object findContents(@RequestBody LinkedHashMap user) {
        String title=user.get("title").toString();
        String sql="select *from table_form where title=\""+title+"\"";
        System.out.println(sql);
        List<table_information> list=rpMapper.search_table(sql);
        String table_name=list.get(0).getName();
        sql="select COLUMN_NAME from information_schema.COLUMNS where table_name=\""+table_name+"\"";
        System.out.println(sql);
        List<Table_name> result=rpMapper.search_tablename(sql);
        List ans=new ArrayList();
        for(int i=0;i<result.size();i++){
            ans.add(result.get(i).getColumn_name());
        }
        return ans;
    }

    @PostMapping("write")
    public void write(@RequestBody LinkedHashMap user) {
        String name=user.get("name").toString();
        String types=user.get("type").toString();
        try{
            FileWriter writer=new FileWriter("F:\\yanxiao\\毕业设计\\demo\\query.txt");
            writer.write(name+" "+types);
            writer.close();
        }catch (IOException e){

        }
//        System.out.println(name+" "+types);
    }

    @PostMapping("findBySth")
    public Object findbysth(@RequestBody LinkedHashMap user) {
        String title=user.get("title").toString();
        List content=(ArrayList)user.get("content");
        String sql="select *from table_form where title=\""+title+"\"";
        System.out.println(sql);
        List<table_information> list=rpMapper.search_table(sql);
        String table_name=list.get(0).getName();
        int type=(Integer)user.get("type");
        List<List<String>> ans=new ArrayList<>();
            String condition=" where ";
            for(int i=0;i<content.size();i++){
                LinkedHashMap data=(LinkedHashMap)content.get(i);
                if(i==0)
                    condition+=data.get("infor")+"=\""+data.get("name")+"\"";
                else
                    condition+=","+data.get("infor")+"=\""+data.get("name")+"\"";
            }
            sql="select *from "+table_name;
            if(!condition.equals(" where ")) sql+=condition;
            sql+=" limit 7";
            System.out.println(sql);
            List<HashMap> result=(ArrayList)rpMapper.find(sql);
            switch (type){
                case 0:
                    for(int i=0;i<result.size();i++){
                        List lists=new ArrayList();
                        if(result.get(i).get("name")==null)
                            lists.add("暂无信息");
                        else
                            lists.add(result.get(i).get("name").toString());
                        lists.add(result.get(i).get("machine_sim_type")==null?"暂无信息":result.get(i).get("machine_sim_type").toString());
                        lists.add(result.get(i).get("makein")==null?"暂无信息":result.get(i).get("makein").toString());
                        lists.add(result.get(i).get("place")==null?"暂无信息":result.get(i).get("place").toString());
                        lists.add(result.get(i).get("buydate")==null?"暂无信息":result.get(i).get("buydate").toString());
                        lists.add(result.get(i).get("country")==null?"暂无信息":result.get(i).get("country").toString());
                        ans.add(lists);
                    }
                    break;
                case 1:
                    for(int i=0;i<result.size();i++){
                        List lists=new ArrayList();
                        if(result.get(i).get("name")==null)
                            lists.add("暂无信息");
                        else
                            lists.add(result.get(i).get("name").toString());
                        lists.add(result.get(i).get("specialty")==null?"暂无信息":result.get(i).get("specialty").toString());
                        lists.add(result.get(i).get("degreesName")==null?"暂无信息":result.get(i).get("degreesName").toString());
                        lists.add(result.get(i).get("workUnit")==null?"暂无信息":result.get(i).get("workUnit").toString());
                        ans.add(lists);
                    }
                    break;
                case 2:
                    for(int i=0;i<result.size();i++){
                        List lists=new ArrayList();
                        if(result.get(i).get("TIVIEW")==null)
                            lists.add("暂无信息");
                        else
                            lists.add(result.get(i).get("TIVIEW").toString());
                        lists.add(result.get(i).get("patentType")==null?"暂无信息":result.get(i).get("patentType").toString());
                        lists.add(result.get(i).get("PR")==null?"暂无信息":result.get(i).get("PR").toString());
                        lists.add(result.get(i).get("INVIEW")==null?"暂无信息":result.get(i).get("INVIEW").toString());
                        ans.add(lists);
                    }
                    break;
                default :
                    if(type<0) break;
                    List<String> names=new ArrayList<>();
                    sql="select COLUMN_NAME from information_schema.COLUMNS where table_name=\""+table_name+"\"";
                    System.out.println(sql);
                    List<Table_name> results=rpMapper.search_tablename(sql);
                    for(int i=0;i<results.size();i++){
                        names.add(results.get(i).getColumn_name());
                    }
                    for(int i=0;i<result.size();i++){
                        List<String> l=new ArrayList<>();
                        for(int j=0;j<names.size();j++){
                            l.add(result.get(i).get(names.get(j)).toString());
                        }
                        ans.add(l);
                    }
                    break;
            }
        return ans;
    }

    @PostMapping("find_init")
    public Object find_init(@RequestBody LinkedHashMap user) {
        LinkedHashMap ans=new LinkedHashMap();
        List<List<String>> infor=new ArrayList<>();
        List<String> name=new ArrayList<>();
        int type=(Integer)user.get("type");
        if(type>=0&&type<=2){
            switch (type){
                case 0:
                    name.add("名称");
                    name.add("类型");
                    name.add("制造厂商");
                    name.add("所在地");
                    name.add("购置日期");
                    name.add("生产国别");
                    String sql="select name,machine_sim_type,makein,place,buydate,country from equipment limit 7";
                    List<HashMap> res=(ArrayList)rpMapper.find(sql);
                    for(int i=0;i<res.size();i++){
                        List list=new ArrayList();
                        list.add(res.get(i).get("name")==null?"暂无信息":res.get(i).get("name").toString());
                        list.add(res.get(i).get("machine_sim_type")==null?"暂无信息":res.get(i).get("machine_sim_type").toString());
                        list.add(res.get(i).get("makein")==null?"暂无信息":res.get(i).get("makein").toString());
                        list.add(res.get(i).get("place")==null?"暂无信息":res.get(i).get("place").toString());
                        list.add(res.get(i).get("buydate")==null?"暂无信息":res.get(i).get("buydate").toString());
                        list.add(res.get(i).get("country")==null?"暂无信息":res.get(i).get("country").toString());
                        infor.add(list);
                    }
                    break;
                case 1:
                    name.add("名字");
                    name.add("专业领域");
                    name.add("学历");
                    name.add("工作单位");
                    sql="select name,specialty,degreesName,workUnit from expert limit 7";
                    res=(ArrayList)rpMapper.find(sql);
                    for(int i=0;i<res.size();i++){
                        List list=new ArrayList();
                        list.add(res.get(i).get("name")==null?"暂无信息":res.get(i).get("name").toString());
                        list.add(res.get(i).get("specialty")==null?"暂无信息":res.get(i).get("specialty").toString());
                        list.add(res.get(i).get("degreesName")==null?"暂无信息":res.get(i).get("degreesName").toString());
                        list.add(res.get(i).get("workUnit")==null?"暂无信息":res.get(i).get("workUnit").toString());
                        infor.add(list);
                    }
                    break;
                case 2:
                    name.add("专利名");
                    name.add("类型");
                    name.add("IPC");
                    name.add("发明人");
                    sql="select TIVIEW,patentType,PR,INVIEW from patent_infor where LANG=\"CN\" limit 7";
                    res=(ArrayList)rpMapper.find(sql);
                    for(int i=0;i<res.size();i++){
                        List list=new ArrayList();
                        list.add(res.get(i).get("TIVIEW")==null?"暂无信息":res.get(i).get("TIVIEW").toString());
                        list.add(res.get(i).get("patentType")==null?"暂无信息":res.get(i).get("patentType").toString());
                        list.add(res.get(i).get("PR")==null?"暂无信息":res.get(i).get("PR").toString());
                        list.add(res.get(i).get("INVIEW")==null?"暂无信息":res.get(i).get("INVIEW").toString());
                        infor.add(list);
                    }
                    break;
            }
        }
        else if(type>2){
            String title=user.get("title").toString();
            String sql="select *from table_form where title=\""+title+"\"";
            System.out.println(sql);
            List<table_information> list=rpMapper.search_table(sql);
            String table_name=list.get(0).getName();
            sql="select COLUMN_NAME from information_schema.COLUMNS where table_name=\""+table_name+"\"";
            System.out.println(sql);
            List<Table_name> result=rpMapper.search_tablename(sql);
            for(int i=0;i<result.size();i++){
                name.add(result.get(i).getColumn_name());
            }
            sql="select * from "+ table_name+" limit 7";
            List<HashMap<String,Object>> res=rpMapper.find(sql);
            for(int i=0;i<res.size();i++){
                List<String> l=new ArrayList<>();
                for(int j=0;j<name.size();j++){
                    if(res.get(i).get(name.get(j))!=null)
                        l.add(res.get(i).get(name.get(j)).toString());
                }
                infor.add(l);
            }
        }
        ans.put("name",name);
        ans.put("infor",infor);
        return ans;
    }

    @PostMapping("CloseConnection")
    public void CloseConnection() throws SQLException {
        this.sqlconnection.close();
    }

    @PostMapping("ResetConnection")
    public void ResetConnection(@RequestBody LinkedHashMap user) throws SQLException {
        int connectNum=(Integer)user.get("connectNum");
        this.sqlconnection.ResetConnection(connectNum);
    }

    @PostMapping("AddConnection")
    public void AddConnection() throws SQLException {
        this.sqlconnection.addConnection(1);
    }

    @PostMapping("GetConnection")
    public Object GetConnection() throws SQLException {
        List<HashMap<String,String>> result=new ArrayList<>();
        LinkedList<ArrayList<Object>> list=this.sqlconnection.getConnection();
        for(int i=0;i<list.size();i++){
            HashMap<String,String> map=new HashMap<>();
            map.put("name",list.get(i).get(0).toString());
            if((Integer)(list.get(i).get(1))==1){
                map.put("type","可使用");
            }
            else{
                map.put("type","已使用");
            }
            result.add(map);
        }
        return result;
    }

    @PostMapping("DeleteConnection")
    public Object DeleteConnection(@RequestBody LinkedHashMap user) throws SQLException {
        int index=(Integer)user.get("index");
        HashMap<Object,Object> map=new HashMap<>();
        if(!this.sqlconnection.deleteConnection(index)){
            map.put("information",1);//删除失败
        }
        else map.put("information",0);
        return map;
    }

    @PostMapping("use")
    public Object use(@RequestBody LinkedHashMap user) throws SQLException {
        int index=(Integer)user.get("index");
        HashMap<Object,Object> map=new HashMap<>();
        boolean ifused=this.sqlconnection.setUsed(index);
        if(ifused){
            map.put("information",1);
        }
        else{
            map.put("information",0);//已经使用
        }
        return map;
    }

    @PostMapping("unuse")
    public Object unuse(@RequestBody LinkedHashMap user) throws SQLException {
        int index=(Integer)user.get("index");
        HashMap<Object,Object> map=new HashMap<>();
        boolean ifused=this.sqlconnection.setUnused(index);
        if(ifused){
            map.put("information",1);//已经使用
        }
        else{
            map.put("information",0);
        }
        return map;
    }

    @PostMapping("check")
    public Object check() throws SQLException {
        String sql="select name,state,staffSize,registerCapital,establishmentDate,telephone,email,businessScope from company limit 5";
        System.out.println(sql);
        List<HashMap<String,Object>> result=rpMapper.find(sql);
        System.out.println(result.toString());
        return result;
    }

    @PostMapping("intelproperty")
    public Object intelproperty() throws SQLException {
        String sql="select name,state,staffSize,registerCapital,establishmentDate,telephone,email,businessScope from intelproperty limit 5";
        System.out.println(sql);
        List<HashMap<String,Object>> result=rpMapper.find(sql);
        System.out.println(result.toString());
        return result;
    }
}
