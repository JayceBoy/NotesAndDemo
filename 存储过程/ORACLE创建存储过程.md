# 存储过程基础

## 1.存储过程结构

### (1).基本结构

Oracle存储过程包含三部分：过程声明，执行过程部分，存储过程异常（可写可不写，要增强脚本的容错性和调试的方便性那就写上异常处理）

### (2).无参存储过程

```SQL
CREATE OR REPLACE PROCEDURE demo AS/IS
	变量2 DATE;
	变量3 NUMBER;
BEGIN
	--要处理的业务逻辑
	EXCEPTION    --存储过程异常
END
```

这里的as和is一样任选一个，在这里没有区别，其中demo是存储过程名称

### (3).有参存储过程

a.带参数的存储过程

```sql
CREATE OR REPLACE PROCEDURE 存储过程名称(param1 student.id%TYPE)
AS/IS
name student.name%TYPE;
age number :=20;
BEGIN
  --业务处理.....
END
```

上面脚本中：

> 第1行：param1 是参数，类型和student表id字段的类型一样。
>
> 第3行：声明变量name，类型是student表name字段的类型（同上）。
>
> 第4行：声明变量age，类型数数字，初始化为20



b.带参数的存储过程并且进行赋值

```sql
CREATE OR REPLACE PROCEDURE 存储过程名称(
       s_no in varchar,
       s_name out varchar,
       s_age number) AS
total NUMBER := 0;
BEGIN
  SELECT COUNT(1) INTO total FROM student s WHERE s.age=s_age;
  dbms_output.put_line('符合该年龄的学生有'||total||'人');
  EXCEPTION
    WHEN too_many_rows THEN 
    DBMS_OUTPUT.PUT_LINE('返回值多于1行'); 
END
```

上面脚本中：

>其中参数IN表 示输入参数，是参数的默认模式。
>OUT表示返回值参数，类型可以使用任意Oracle中的合法类型。
>OUT模式定义的参数只能在过程体内部赋值，表示该参数可以将某个值传递回调用他的过程
>IN OUT表示该参数可以向该过程中传递值，也可以将某个值传出去
>
>第7行：查询语句，把参数s_age作为过滤条件，INTO关键字，把查到的结果赋给total变量。
>
>第8行：输出查询结果，在数据库中“||”用来连接字符串
>
>第9—11行：做异常处理

### (3).SELECT INTO STATEMENT语句

该语句将select到的结果赋值给一个或多个变量，例如：

```sql

CREATE OR REPLACE PROCEDURE DEMO_CDD1 IS
s_name VARCHAR2;   --学生名称
s_age NUMBER;      --学生年龄
s_address VARCHAR2; --学生籍贯
BEGIN
  --给单个变量赋值
  SELECT student_address INTO s_address
  FROM student where student_grade=100;
   --给多个变量赋值
  SELECT student_name,student_age INTO s_name,s_age
  FROM student where student_grade=100;
  --输出成绩为100分的那个学生信息
  dbms_output.put_line('姓名：'||s_name||',年龄：'||s_age||',籍贯：'||s_address);
END
```

上面脚本中：

> 存储过程名称：DEMO_CDD1, student是学生表，要求查出成绩为100分的那个学生的姓名，年龄，籍贯

### (4).选择语句

a.IF..END IF

```sql
IF s_sex=1 THEN
  dbms_output.put_line('这个学生是男生');
END IF;
```

b.IF..ELSE..END IF

```sql
IF s_sex=1 THEN
  dbms_output.put_line('这个学生是男生');
ELSE
  dbms_output.put_line('这个学生是女生');
END IF;
```

### (5).循环语句

a.基本循环

```sql
LOOP
  IF 表达式 THEN
    EXIT;
  END IF
END LOOP;
```

b.while循环

```sql
WHILE 表达式 LOOP
  dbms_output.put_line('hello,jayce!')
END LOOP;
```

c.for循环

```sql
FOR a IN 10..20 LOOP
  dbms_output.put_line('a的值是：' || a)
END LOOP;
```



原文地址：[https://blog.csdn.net/weixin_41968788/article/details/83659164#%E4%BA%8C.%E4%B8%BA%E4%BB%80%E4%B9%88%E8%A6%81%E5%86%99%E5%AD%98%E5%82%A8%E8%BF%87%E7%A8%8B](https://blog.csdn.net/weixin_41968788/article/details/83659164#二.为什么要写存储过程)