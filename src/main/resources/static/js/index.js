/**
 * Created by Lenovo on 2019-06-11.
 */
$(function(){


    $.ajax({

        url: "http://localhost:8080/api/user/getUserList/",

        type: "GET",

        dataType: 'json',

        contentType: "application/json;charset=utf-8",

        success: function (data) {

            //成功连接api接口

            if (data.status == "1000") {

                //获取api接口传过来的数据

                var list = data.data.userDOList;
                $("#tbMain").empty();
                var tr = "<tr>" +

                    "<td></td>" +

                    "<td bgcolor='#5f9ea0'>" + "用户ID" + "</td>" +

                    "<td bgcolor='#5f9ea0' >" + "用户姓名" + "</td>" +

                    "<td bgcolor='#5f9ea0'>" + "用户住址" + "</td>" +

                    "<td bgcolor='#5f9ea0'>" + "email" + "</td>" +

                    "<td bgcolor='#5f9ea0'>" + "手机"+ "</td>" +

                    "<td bgcolor='#5f9ea0'>" +"操作" +"</td>" +

                    "</tr>";
                $("#tbMain").append(tr);

                //alert(list.length);
                for (var i = 0; i < list.length; i++) {

                    var ids = list[i].userId;

                    var name = list[i].name;

                    var email = list[i].email;

                    var mobile = list[i].mobile;

                    var liveAddress = list[i].liveAddress;

                    var idsddd = ids;

                    //输出格式并填充

                    //1.复选框；2.数据库里的序号；3.楼宇名称；4.地址；5.产权单位；6.市；7.编辑按钮。

                    //ajax里按钮只能用input属性。


                    var tr = "<tr>" +

                        "<td><input type='checkbox' name='checkbox' value='" + ids + "'/>" + "</td>" +

                        "<td>" + ids + "</td>" +

                        "<td>" + name + "</td>" +

                        "<td>" + liveAddress + "</td>" +

                        "<td>" + email + "</td>" +

                        "<td>" + mobile + "</td>" +

                        "<td><input type='button' id='edit' name='edit' value='编辑'>" + "</td>" +

                        "</tr>";

                    //将查得的数据每一条都插入到表格中

                    $("#tbMain").append(tr);

                }

            }

        },

        error: function (content) {

            alert("失败");

        }

    });



$("input[id='search']").click(function(){

    //清空table界面里的填充数据，但保留css样式及表头

    //$("#dataTablestr:not(:first)").empty("");

    $("#tbMain").empty();
    //编写url后缀格式
    //alert("ceshi");
    var string_search;

    var string_search0 ="name=";

    var string_search1 = "liveAddress=";

    var string_search2 ="mobile=";

    //获取搜索框的值

    var buddingname_ss =$('#username_search').val();

    var address_ss = $('#address_search').val();

    var propertyunit_ss =$('#mobile_search').val();

    //判断搜索框的值是否为空

    if (buddingname_ss != '') {

        string_search = string_search0 +buddingname_ss;

    }else{
        string_search = string_search0;
    }

    if (address_ss != '') {

        string_search += "&"+ string_search1 + address_ss;

    }
    else{
        string_search += "&"+ string_search1;
    }

    if (propertyunit_ss != '') {

        string_search += "&"+ string_search2 + propertyunit_ss;

    }
    else{
        string_search += "&"+ string_search2;
    }



    $.ajax({

        url:"http://localhost:8080/api/user/getUserByCondition?"+string_search,

        type:"GET",

        dataType:'json',

        contentType:"application/json;charset=utf-8",



        success:function(data){

            console.log(data);

            console.log(data.msg);

            if(data.status =="1000"){


                var list = data.data.userDOList;
                $("#tbMain").empty();
                var tr = "<tr>" +

                    "<td></td>" +

                    "<td bgcolor='#5f9ea0'>" + "用户ID" + "</td>" +

                    "<td bgcolor='#5f9ea0' >" + "用户姓名" + "</td>" +

                    "<td bgcolor='#5f9ea0'>" + "用户住址" + "</td>" +

                    "<td bgcolor='#5f9ea0'>" + "email" + "</td>" +

                    "<td bgcolor='#5f9ea0'>" + "手机"+ "</td>" +

                    "<td bgcolor='#5f9ea0'>" +"操作" +"</td>" +

                    "</tr>";
                $("#tbMain").append(tr);


                for (var i = 0; i < list.length; i++) {

                    var ids = list[i].userId;

                    var name = list[i].name;

                    var email = list[i].email;

                    var mobile = list[i].mobile;

                    var liveAddress = list[i].liveAddress;

                    var idsddd = ids;

                    //输出格式并填充

                    //1.复选框；2.数据库里的序号；3.楼宇名称；4.地址；5.产权单位；6.市；7.编辑按钮。

                    //ajax里按钮只能用input属性。


                    var tr = "<tr>" +

                        "<td><input type='checkbox' name='checkbox' value='" + ids + "'/>" + "</td>" +

                        "<td>" + ids + "</td>" +

                        "<td>" + name + "</td>" +

                        "<td>" + liveAddress + "</td>" +

                        "<td>" + email + "</td>" +

                        "<td>" + mobile + "</td>" +

                        "<td><input type='button' id='edit' name='edit' value='编辑'>" + "</td>" +

                        "</tr>";

                    //将查得的数据每一条都插入到表格中

                    $("#tbMain").append(tr);

                }

            }

        },

        error: function(content) {

            alert("失败");

        }

    });



});
    $("input[id='delete_buildInfor']").click(function(){

        // 编辑url后缀格式

        var ids;

        var stringIds_1 = "ids=";

        var stringIds;



        // 获取复选框选定状态

        var checkboxs =document.getElementsByName("checkbox");

        for(i=0;i<checkboxs.length;i++){

            if(checkboxs[i].checked == true){

                ids = checkboxs[i].value;

                if (i == 0) {

                    stringIds =  stringIds_1 + ids;

                }else{

                    stringIds +="&" + stringIds_1 + ids;

                }

            }

        }

        $.ajax({

            url:"http://localhost:8080/api/user/deleteUser?"+stringIds,

            type:"GET",

            dataType:'json',

            contentType:"application/json;charset=utf-8",

            success:function(data){

                console.log(data);

                console.log(data.msg);



                if(data.status =="1000"){

                    alert("删除成功!");

                    //重新刷新界面

                    window.location.href ="http://localhost:8080/user/index";

                }

                else{

                    alert("删除失败，请重试!");

                }

            },

            error: function(content) {

                alert("失败");

            }

        });

    });


    $(document).on('click','#edit', function () {

        //获取该行id，在此序号列为id值。

        var td_get = $(this).parents("tr").find("td");

        var id_get = td_get.eq(1).text();



        $.ajax({

            url:"http://localhost:8080/api/user/getUserById/"+id_get,

            type:"GET",

            dataType:'json',

            contentType:"application/json;charset=utf-8",

            success:function(data){

                if(data.status =="1000"){



                    //获取该id的所有属性值

                    var id = data.data.userId;

                    var buddingname =data.data.name;

                    var address = data.data.liveAddress;

                    var propertyunit =data.data.email;

                    var region = data.data.mobile;



                    //清空当前界面
                    $("#light1").empty();
                    //$("#dataTables").empty("");
                    //
                    //$("#jz").empty("");
                    //
                    //$("#ys").empty("");



                    //将id的所有属性值，赋值到编辑界面，即可编辑的文本框中

                    //disabled='disabled' 表示不可更改

                    var text = "<form>"+

                        "用户id："+ "<br>" + "<input type='text' value='"+id+"' id='id_edit' disabled='disabled'>"+"</br>"+

                        "<br>" +"用户名称："+ "<br>" +"<input type='text' value='"+buddingname+"'id='buddingname_edit'>"+ "</br>"+

                        "<br>" + "用户地址："+ "<br>" +"<input type='text' value='"+address+"'id='address_edit'>"+ "</br>"+

                        "<br>" + "email："+ "<br>" +"<input type='text' value='"+propertyunit+"'id='propertyunit_edit'>"+ "</br>"+

                        "<br>" + "手机："+ "<br>" +"<input type='text' value='"+region+"'id='region_edit'>"+ "</br>"+

                        "<br>" +"<input type='button' id='save_edit' value='保存'><input type='button' id='cancel_edit'value='取消'>"+"<br>" +

                        "</form>";

                    $("#light1").append(text);

                }

            },

            error: function(content) {

                alert("失败");

            }

        });





    });



//在编辑界面，取消editTd编辑按钮

    $(document).on('click','#cancel_edit', function () {

        //此方法是自己写的，局部刷新页面，重新加载数据

        window.location.href="http://localhost:8080//qrPrint/api/index";

    });



//在编辑界面，保存editTd编辑按钮

    $(document).on('click','#save_edit', function () {

        //获取编辑界面里，文本框内的值

        var id_save =$("#id_edit").val();

        var buddingname_save =$("#buddingname_edit").val();

        var address_save =$("#address_edit").val();

        var propertyunit_save = $("#propertyunit_edit").val();

        var region_save =$("#region_edit").val();

        var data={userId:id_save,name:buddingname_save,liveAddress:address_save,email:propertyunit_save,mobile:region_save};

        //输出当前td值

        $.ajax({

            type:"POST",

            contentType:'application/json;charset=UTF-8',

            url:"http://localhost:8080/api/user/updateUser",

            //将值转换为api可接受的json格式

            dataType:'json',

            data:JSON.stringify(data),

        //data:
        //{id:id_save,buddingname:buddingname_save,address:address_save,propertyunit:propertyunit_save,region:region_save},

            success:function(data){

                //console.log(data);
                //
                //console.log(data.msg);



                if(data.status =="1000"){

                    alert("更新成功!");

                    window.location.href ="http://localhost:8080/user/index";

                }

                else{

                    alert("更新失败，请重试!");

                }



            },

            error: function(content) {

                alert("失败");

            }

        });





    });


    $(document).on('click','#add_buildInfor',function(){
        //alert("tianjia");
        var text = "<form>"+

            "userid："+ "<br>" + "<input type='text' value='"+"' id='id_add' disabled='disabled'>"+"</br>"+

            "<br>" +"用户昵称："+ "<br>" +"<input type='text' value='"+"'id='username_edit'>"+ "</br>"+

            "<br>" + "名字："+ "<br>" +"<input type='text' value='"+"'id='name_edit'>"+ "</br>"+

            "<br>" + "住址："+ "<br>" +"<input type='text' value='"+"'id='liveAddress_edit'>"+ "</br>"+

            "<br>" + "手机："+ "<br>" +"<input type='text' value='"+"'id='mobile_edit'>"+ "</br>"+

            "<br>" +"<input type='button' id='add_btn' value='添加'><input type='button' id='cancel_edit'value='取消'>"+"<br>" +

            "</form>";

        $("#add_house").append(text);

    });


    //在添加界面，保存add添加按钮

    $(document).on('click','#add_btn', function () {

        //获取编辑界面里，文本框内的值

        var id_save =$("#id_edit").val();

        var buddingname_save =$("#username_edit").val();

        var address_save =$("#name_edit").val();

        var propertyunit_save = $("#liveAddress_edit").val();

        var region_save =$("#mobile_edit").val();

        var data={userId:id_save,username:buddingname_save,name:address_save,liveAddress:propertyunit_save,mobile:region_save};

        //输出当前td值

        $.ajax({

            type:"POST",

            contentType:'application/json;charset=UTF-8',

            url:"http://localhost:8080/api/user/addUser",

            //将值转换为api可接受的json格式

            dataType:'json',

            data:JSON.stringify(data),

            //data:
            //{id:id_save,buddingname:buddingname_save,address:address_save,propertyunit:propertyunit_save,region:region_save},

            success:function(data){

                //console.log(data);
                //
                //console.log(data.msg);



                if(data.status =="1000"){

                    alert("添加成功!");

                    window.location.href ="http://localhost:8080/user/index";

                }

                else{

                    alert("更新失败，请重试!");

                }



            },

            error: function(content) {

                alert("失败");

            }

        });





    });


});
function  query() {
    $.ajax({

        url: "http://localhost:8080/api/getHouse/",

        type: "GET",

        dataType: 'json',

        contentType: "application/json;charset=utf-8",

        success: function (data) {

            //成功连接api接口

            if (data.status == "1000") {

                //获取api接口传过来的数据

                var list = data.data.list;
                $("#tbMain").empty();

                for (var i = 0; i < list.length; i++) {

                    var ids = list[i].id;

                    var buddingname = list[i].buddingname;

                    var address = list[i].address;

                    var propertyunit = list[i].propertyunit;

                    var region = list[i].region;

                    var idsddd = ids;

                    //输出格式并填充

                    //1.复选框；2.数据库里的序号；3.楼宇名称；4.地址；5.产权单位；6.市；7.编辑按钮。

                    //ajax里按钮只能用input属性。


                    var tr = "<tr>" +

                        "<td><input type='checkbox' name='checkbox' value='" + ids + "'/>" + "</td>" +

                        "<td>" + ids + "</td>" +

                        "<td>" + buddingname + "</td>" +

                        "<td>" + address + "</td>" +

                        "<td>" + propertyunit + "</td>" +

                        "<td>" + region + "</td>" +

                        "<td><input type='button' id='edit' name='edit' value='编辑'>" + "</td>" +

                        "</tr>";

                    //将查得的数据每一条都插入到表格中

                    $("#tbMain").append(tr);

                }

            }

        },

        error: function (content) {

            alert("失败");

        }

    });
}