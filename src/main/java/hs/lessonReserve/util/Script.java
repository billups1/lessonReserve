package hs.lessonReserve.util;

public class Script {

    public static String back(String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('"+msg+"');");
        sb.append("history.back()");
        sb.append("</script>");
        return sb.toString();
    }

    public static String reload(String msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('"+msg+"');");
        sb.append("location.reload()");
        sb.append("</script>");
        return sb.toString();
    }

}