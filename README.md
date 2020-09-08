# Kocfinans-Demo Full Stack Mini Proje
Microservis yaklaşımıyla önyüzden kullanıcıdan aldığı bilgileri arka tarafta bir kredi başvuru 
servisine iletilmesi ve ilgili kredi başvuru sonucunun 
kullanıcıya gösterilip sms atılması ve dbye kaydedilmesi üzerine yapılmış bir projedir.

## Proje Detayları
-Proje modüler bir yapıdadır.İçerisinde 4 modul barındırmakdır,
1. credit
   1. account endpoint barındırır, müsteri bilgilerini mysqlden almamızı sağlar
   2. credit enpointi barındırır, formdan gelen bilgiler alınır ve credi servisine yollanır
      1. aldığı verileri doğrular, daha sonra creditscore servisten müşteri skorunu alır
      2. algoritma dogrultusunda işlemler yapar
          * Kredi skoru 500’ün altında ise kullanıcı reddedilir. (Kredi sonucu: Red)
          * Kredi skoru 500 puan ile 1000 puan arasında ise kredi başvurusu onaylanır ve
              1. Aylık geliri 5000 TL’nin altında ise kullanıcının  ve kullanıcıya 10.000 TL lmt atanır.
                  Kredi Sonucu: Onay)
              2. Aylık geliri 5000 TL’nin üstünde ise kullanıcının  ve kullanıcıya  AYLIK GELİR BİLGİSİ *
                  KREDİ LİMİT ÇARPANI (3) kadar limit atanır. (Kred Sonucu: Onay)
                  
          * Kred skoru 1000 puana eşit veya üzerindeyse kullanıcıya AYLIK GELİR BİLGİSİ *
                  KREDİ LİMİT ÇARPANI kadar limit atanır. (Kred Sonucu: Onay)
       1. kredi sonucu hesapladıktan sonra MYSQLde dbye kaydeder
       2. kredi sonucunu sms olarak atılması icin kafkada sms topigine tc,sonuc ve limit bilgisini yollar.
       3. kredi sonucunu(Onay veya Red,Limit bilgisini) önyüze gönderilmek üzere endpointe geri yollar.
2. creditscoreservice
   1. eurokaservis üzerinden gelen istekleri karsılar
      1. microservis yapıdadır
      1. 0 ile 1500 arasında sayı return eder

 
3. eurokaserver
   1. Microservis tabanlı uygulamalar bu server üzerinden haberleşir.
4. sms_service
    1. kafkada topigi dinler, microservis yapıdadır MONGO DB ile calısır
        * sms atılacak müsterinin bilgisini dinler daha sonra smsservisine iletir
    2. sms service aldığı sms bilgileriyle eurekaserver üzerinden creditin account servisiyle iletisime gecer
        * gelen Tc No üzerinden account servisinden müsteri bilgilerini alır
        * sms atar ve sms attığını MONGO DBye kayıt atar.
        
        
## Kurulum İçin Gerekli Detaylar
  * MYSQL SERVER KURULU OLMALI -> Credit modulu MYSQL SERVER kullanıyor 
     * fullstack_kf veritabanı yaratılmalı
     * mysql bilgileri application propertiesten gilirmelidir username password localhost gibi
  * Kafka ve Zookeeper kurulu olmalı ve projeden önce ayağa kaldırılması lazım
  * MONGO DB Server çalışıyor olması lazım
  * Önce EurekaServer modülü çalıştırılmalı daha sonra diğer modüller ayağa kalkmalı
  * Bütün modüller çalıştığında localhost:8081 den projenin çalışır olduğu görülebilir
  * swagger dökümantasyonuyla 8081 8082 8083 portlarından apilerle işlemler yapabilirsiniz.
  * Daha sonra kocfinans_ui repositorisi indirilip çalıştırılmalı
     * React.js projesidir
     * Node.js ve react kurulu olmalıdır.
     * npm start komutuyla önyüz calıstırılır
    ```javascript
      npm start
      ```
## Kurulum
```
git clone https://github.com/titanotank/kocfinans-demo.git
```

Daha sonra IDE kullanılarak proje açılır, proje açılınca pom.xml'e göre proje bağımlılıkları indirecektir.
Daha sonra mysql bilgileri credit modülündeki appplication.propertiesda ayarlanır.
Proje jdk 11 kullanıyor. ideden proje compilerını değiştirebilirsiniz eğer uyumlu değilse.

Kafka zookeper ve MongoDB yi indirip çalıştırmanız yeterlidir bir ayarlama yapmaya gerek yoktur.

## Proje Çalıstırılması
1. EurekaServer modulündeki EurekaServer applicationi ilk olarak çalıştırılır. localhost:9999 dan görüntülenebilir.
2. Diğer modüllerdeki projelerin hepsi çalıştırılır.
3. localhost:8081 8082 8083 ten swagger dökümanlarından bakılabilir.


## Proje Mimarisi
![FullStack-KocFinans](https://user-images.githubusercontent.com/26394549/92422955-eb5f6d00-f187-11ea-9b3e-ef4a51960d16.png)

##Proje Önyüzü
Uyarı proje önyüzünü  calıstırmak icin kocfinans-ui projesinin indirip calıstırmalısız. Fullstack projenin bir parcası olduğu icin ekran görüntüsünü buraya koyuyorum

## Form Ekranı
![KrediBasvuruEkranı](https://user-images.githubusercontent.com/26394549/92422945-e995a980-f187-11ea-9962-50cbcbabaa2e.png)

## Sonuc Ekranı
![KrediOnayEkranı](https://user-images.githubusercontent.com/26394549/92422951-eac6d680-f187-11ea-9292-68671ba5a912.png)
