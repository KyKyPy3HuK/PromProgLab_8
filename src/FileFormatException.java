public class FileFormatException extends Throwable{
    String message;
    public FileFormatException(){
        message = "Неизвестная ошибка формата файла";
    }
    public FileFormatException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
