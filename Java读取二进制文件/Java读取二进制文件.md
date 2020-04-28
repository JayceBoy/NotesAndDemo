## Java读取二进制文件

```java
//读取二进制文件，并存入byte数组
public byte[] readFromByteFile(String pathname) throws IOException{
    File filename = new File(pathname);
    BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
    ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
    byte[] temp = new byte[1024];
    int size = 0;
    while((size = in.read(temp)) != -1){
        out.write(temp, 0, size);
    }
    in.close();
    byte[] content = out.toByteArray();
    return content;
}
```

