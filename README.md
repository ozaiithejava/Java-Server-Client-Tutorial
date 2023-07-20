# Java-Server-Client-Tutorial


# **Soket Nedir**

## server nedir ?

Server (Sunucu): Server, bir bilgisayar programı veya donanımıdır. Ağ üzerindeki diğer cihazlara (istemcilere) hizmet sunan bir bilgisayar sistemidir. İstemcilerin taleplerini karşılar, kaynakları sağlar ve işlemleri gerçekleştirir. Sunucular, web sitelerini barındırma, e-posta gönderme ve alma, dosya depolama, veritabanı yönetimi gibi farklı hizmetleri sağlayabilir.

---------------------------------------------------------------------------------------------------------------------------------------------------------

## client nedir ?

Client (İstemci): İstemci, bir ağ üzerindeki sunuculara bağlanan bir bilgisayar programı veya donanımıdır. Sunucudan hizmet talep eden ve sunucudan aldığı hizmetlerle işlem yapan cihazdır. Web tarayıcıları, e-posta istemcileri ve dosya transfer istemcileri gibi farklı türde istemci yazılımlar bulunmaktadır. İstemciler, kullanıcıların sunucudan hizmet almak veya veri göndermek için kullandığı araçlardır.

---------------------------------------------------------------------------------------------------------------------------------------------------------

## pekala iyi hoşta bu socket nedir ?

Socket, ağ üzerindeki bilgisayarlar arasında veri iletişimi sağlayan bir arayüzdür. Bir socket, iki makine arasındaki bağlantıyı temsil eder ve bu bağlantı üzerinden verilerin akışını mümkün kılar. Soketler, TCP (Transmission Control Protocol) veya UDP (User Datagram Protocol) gibi farklı iletişim protokollerini kullanarak verileri gönderip alabilirler.

### örnek bir soket bağlantısı (SERVER) Tarafı:

     public static void listenerServer() throws IOException {
        try {
            System.out.println("Waiting any Clients");
            ServerSocket ss = new ServerSocket(4433);
            Socket soc = ss.accept();
            System.out.println("Connected the client");

        }catch (IOException e){
            e.printStackTrace();
        }


 ** Socket soc = ss.accept();** soc objesi Socket sınıfından türetilip ss objesi için tanımlanmış (yani serversocket) ve gelen requestleri yani istekleri kabul et diyor 

### örnek bir soket bağlantısı (CLIENT) Tarafı:

    public static void main(String[] args) throws IOException {
		 try {
			System.out.println("Client waiting for connected the server");
			Socket soc = new Socket("localhost",4433);//connect the port
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

### Şimdi bu kodlar nedir anlatalım bakalım :
      ServerSocket ss = new ServerSocket(4433);

bu kod ServerSocket Sınıfını baz alarak ss diye bir obje üretmemizi sağlar ve sonra onu newler 4433 olan int değerimiz portumuzdur 

#### pekala port nedir ? 

Port, ağ üzerindeki veri iletişimi için belirli bir hedef adresi tanımlayan sayısal bir değerdir. Bir bilgisayarın bir ağ üzerindeki diğer bir bilgisayara veya hizmete veri göndermek veya almak için belirli bir port numarası üzerinden bağlanması gerekir.

Port numaraları 0 ile 65535 arasındadır ve belirli bir servis veya uygulamanın bağlanacağı veya dinleyeceği port numarası genellikle servis veya uygulama tarafından belirlenir. Örneğin, HTTP protokolü için 80 numaralı port kullanılır, HTTPS için 443 numaralı port kullanılır, FTP için 21 numaralı port kullanılır vb.

Java'da, ServerSocket ve Socket sınıflarını kullanarak ağ üzerindeki iletişimde port numaralarını belirleyebilirsiniz. ServerSocket nesnesi belirli bir port numarası üzerinden gelen bağlantıları dinlemek ve kabul etmek için kullanılırken, Socket nesnesi belirli bir IP adresi ve port numarası üzerinden sunucuya veya başka bir istemciye bağlanmak için kullanılır.

### şimdi server de port açmayı öğrendik peki client tarafında neler oluyor izleyelim:

      System.out.println("Client waiting for connected the server");
			Socket soc = new Socket("localhost",4433);//connect the port

burada da soc adlı bir obje üretiyoruz fakat ServerSocket sınıfından baz alarak değil **Socket** sınıfından kalıtılarak üretiyoruz 
buda bizim soket bağlantımızın Client olduğunu gösteriyor localhost demek kendi makinem üzerindeki ip adresim demektir 
**4433** de sunucu ile aynı port ( unutmayın aynı port olması lazım )


---------------------------------------------------------------------------------------------------------------------------------------------------------

## Veri alma girdi çıktı işlemleri :

### ServerSide(Sunucu Taraflı) 

             try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Listening on port 12345...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from: " + clientSocket.getInetAddress());

            // Client'tan gelen verileri okuyalım ve ekrana yazdıralım
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Client: " + clientMessage);
            }

            // Client'a hoşgeldin mesajı gönderelim
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("Merhaba, bağlantı başarılı!");

            // İşlem tamamlandıktan sonra soketleri kapat
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

BufferedReader sınıfı yardımı ile verileri okuyacağız in diye obj oluşturduk bu obje InputStreamReader sınıfı yardımı ile  okuma almamıza yarıyıcak 
gelen dönütleri while döngüsü yardımı ile konsolumuza yazdırıyoruz Client ten gelen dönütleri alıyoruz
Daha sonrasında OutputStream sınıfından türettiğimiz out adlı objemiz ile PrintWriter sınıfını kullanarak Cliente mesaj yolluyoruz


## NOT : **Unutmayın bu işlemlerin sağlıklı ve çalışması ve yaptığınız hataların daha kolay ayıklanabilmesi adına try-cath kullanmalısınız**

### ClientSide kodlarımız:

    try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server at localhost:12345");

            // Server'a hoşgeldin mesajını gönderelim
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("Merhaba, bu bir istemci mesajı!");

            // Server'dan gelen verileri okuyalım ve ekrana yazdıralım
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Server: " + serverMessage);
            }

            // İşlem tamamlandıktan sonra soketi kapat
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

Buradaki olay Serverin bize mesaj gönderdiği gibi bizim ona mesaj yollamamızla neredeyse aynı mantık 
BufferedReader ile mesajımızı alıyoruz InputStreamReader yardımı ile okumayı gerçekleştiriyoruz

işlem tamamlandıktan sonra **socket.close();** ile bağlantıya son veriyoruz peki oop de bunu nasıl kullanacagız aslında oop değilde inLife diyelim
misal bir discord tarzı Client yazdınız ve ÇıkışYap butunu koydunuz System.exit(0); kullanmadan önce soket bağlantısını kapatabilirsiniz.

---------------------------------------------------------------------------------------------------------------------------------------------------------

### Pekala bukadar ilerledik dahada zorlaştıralım işlemleri hwid yani computer bilgisi nasıl yollayabiliriz:
**NOT: Bu illegal olabilir Lütfen test amaçlı kullanalım Yasal yükümlülük tamami ile size aittir bilgi hırsızlığına karşıyız**

## Aynı Şekilde 2taraflı kodlarımızı yazacağız 

### ServerSide:

    import java.io.*;
    import java.net.*;

    public class TwoWayServer {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Server started. Listening on port 12345...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from: " + clientSocket.getInetAddress());

            // Client'in HWID'sini alalım
            String clientHwid = getClientHwid(clientSocket);
            System.out.println("Client HWID: " + clientHwid);

            // Client'tan gelen verileri okuyalım ve ekrana yazdıralım
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientMessage;
            while ((clientMessage = in.readLine()) != null) {
                System.out.println("Client: " + clientMessage);
            }

            // Client'a hoşgeldin mesajı gönderelim
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("Merhaba, bağlantı başarılı!");

            // İşlem tamamlandıktan sonra soketleri kapat
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getClientHwid(Socket clientSocket) throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        return in.readLine(); // Client tarafından gönderilen HWID'yi alıyoruz
    }
}


burada yeni static bir method karşılıyor bizleri getClientHwid methodumuz bu methodumuz Socket sınıfı için bir obje belirtmemizi istiyor bizden 
bizde yukardaki methodumuzda belirtiyoruz örn: ** String clientHwid = getClientHwid(clientSocket);**
bu şekilde hwid yani bilgilerimizi serverimiz ile alıyoruz 

### CLIENTSIDE

    import java.io.*;
    import java.net.*;
    import java.util.UUID;
 
    public class TwoWayClient {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server at localhost:12345");

            // Client HWID'sini oluşturalım ve server'a gönderelim
            String hwid = getHWID();
            System.out.println("Client HWID: " + hwid);

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println(hwid);

            // Server'dan gelen verileri okuyalım ve ekrana yazdıralım
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String serverMessage;
            while ((serverMessage = in.readLine()) != null) {
                System.out.println("Server: " + serverMessage);
            }

            // İşlem tamamlandıktan sonra soketi kapat
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getHWID() {
        // Basit bir şekilde rastgele bir UUID (Universal Unique Identifier) oluşturuyoruz.
        return UUID.randomUUID().toString();
    }
}

buradaki mevzu aslında aynı yorum satırlarında belirttiğim yerleri okuyun anlayacaksınız ufak tefek farklılıklar var 

server 'e bağlanınca serverin konsolunda cıkan yazı

Server started. Listening on port 12345...

Client connected from: /xxx.xxx.x.x

Client HWID: xxxxxx.xxxxxx.xxxxxx.xxxxx

ilerki günlerde devamı eklenecektir buraya kadar okudu iseniz star atmayı unutmayınız :)
