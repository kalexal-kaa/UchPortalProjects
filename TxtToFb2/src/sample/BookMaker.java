package sample;

class BookMaker {
    String fb2Creator(String firstName, String middleName, String lastName, String bookName, String annotation, String date, String text,String image){
        String coverPage,binary;
        if (image!=null){
            coverPage= "      <coverpage>\n" +
                    "        <image l:href=\"#coverpage\"/>\n" +
                    "      </coverpage>\n";
            binary= "   <binary id=\"coverpage\" content-type=\"image/png\">\n" +
                    "    "+image+"\n" +
                    "    </binary>\n";
        }else {
            coverPage="";
            binary="";
        }
        String fb2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<FictionBook xmlns=\"http://www.gribuser.ru/xml/fictionbook/2.0\" xmlns:l=\"http://www.w3.org/1999/xlink\">\n" +
                "  <description>\n" +
                "    <title-info>\n" +
                "      <author>\n" +
                "        <first-name>"+firstName+"</first-name>\n" +
                "        <middle-name>"+middleName+"</middle-name>\n" +
                "        <last-name>"+lastName+"</last-name>\n" +
                "      </author>\n" +
                "      <book-title>"+bookName+"</book-title>\n" +
                "      <annotation>\n" +
                "        <p>"+annotation+"</p>\n" +
                "      </annotation>\n" +
                "      "+coverPage+"\n" +
                "    </title-info>\n" +
                "    <document-info>\n" +
                "      <program-used>TxtToFb2Converter</program-used>\n" +
                "      <date>"+date+"</date>\n" +
                "    </document-info>\n" +
                "  </description>\n" +
                "  <body>\n" +
                "    <title>\n" +
                "      <p>"+bookName+"</p>\n" +
                "    </title>\n" +
                "    <section>\n" +
                "      "+text+"\n" +
                "    </section>\n" +
                "  </body>\n" +
                "    "+binary+"\n" +
                "</FictionBook>";
        return fb2;
    }
}
