package Assignment4;

import javax.xml.bind.DatatypeConverter;

public class InstanceCallbackDigestUserInterface {
    private String filename;
    private byte[] digest;

    public InstanceCallbackDigestUserInterface(String filename){
        this.filename = filename;
    }

    public void calculateDigest(){
//        System.out.println("calculate");
        InstanceCallbackDigest callbackDigest = new InstanceCallbackDigest(filename, this);
        Thread thread = new Thread(callbackDigest);
//        System.out.println("start");
        thread.start();
    }

    void receiveDigest(byte[] digest){
//        System.out.println("receive");
        this.digest = digest;
        System.out.println(this);
    }

    @Override
    public String toString(){
        String result = filename + ": ";
        if(digest!=null){
            result += DatatypeConverter.printHexBinary(digest);
        }else{
            result += "digest not available";
        }
        return result;
    }

    public static void main(String[] args) {
        for(String filename: args){
            InstanceCallbackDigestUserInterface d = new InstanceCallbackDigestUserInterface(filename);
            d.calculateDigest();
        }
    }
}
