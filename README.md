# **Sunum içeriği**

 - ### [0. Giriş](#giris)
 - ### [1. Aktivite Yönetimi](#aktivite-yonetimi)
 - ### [2. Uygulama Aktivite kaydı](#uygulama-aktivite-kaydi)
 - ### [3. Ürün liste yapısını oluşturma](#urun-liste-yapisini-olusturma)
 - ### [4. Ürün yapılarını listeye ekleme](#urun-yapilarini-listeye-ekleme)
 - ### [5. Ürünleri fiyatlarına göre listeleme](#urunleri-fiyatlarina-gore-listeleme)
 - ### [6. Kullanıcı Girişi](#kullanici-girisi)
 - ### [7. Ürünlerin veritabanı'na kayıt edilmesi](#urunlerin-veritabanina-kayit-edilmesi)
 - ### [8. Ürünlerin listeye eklenmesi](#urunlerin-listeye-eklenmesi)
 - ### [9. Aktiviteler arası geçiş ile uygulamanın tamamlanması](#aktiviteler-arasi-gecis-ile-uygulamanin-tamamlanmasi)


<a id="giris"></a>
## 0. Giriş

Mobil programlama final projesinde yapmış olduğum uygulamanın amacı;
kullanıcı tarafından girilen bilgiler ile kullanıcının ürünlerinin
Firebase Firestore veritabanına kayıt edilmesi ve uygulama arayüzünden mevcut
veritabanında bulunan ürünlerin görüntülenebilmesini sağlamaktır.

<a id="aktivite-yonetimi"></a>
## 1. Aktivite Yönetimi

Projemin ilk aşamasında, uygulama'nın aktivitelerinden;
 Landing: uygulama ilk açıldığında kullanıcıyı karşılayan, 
 Main: kayıtlı ürünlerin listelendiği,
 Login: kullanıcıların ürün kayıt edebilmesi için gerekli girişi sağladıkları,
 ve Product: ürünlerin kayıt işleminin gerçekleştiği 
aktivite olarak 4 ana aktivitemi oluşturdum.

<a id="uygulama-aktivite-kaydi"></a>
## 2. Uygulama Aktivite Kaydı

Aktivitelerimi oluşturduktan sonra bu aktiviteleri uygulamaya tanıtmak üzere,
AndroidManifest dosyasında bu aktiviteleri kayıt ettim.

<a id="urun-liste-yapisini-olusturma"></a>
## 3. Ürün liste yapısını oluşturma

Aktivitelerimizin yapısını oluşturduktan sonra, uygulamamızın Main aktivitesinde
listelemek istediğimiz ürünlerin liste yapısını RecyclerView ve CardView elementlerinden
yararlanarak oluşturdum.

CardView elementi içerisinde, ürünlerin listede göstermek istediğim özelliklerini
ekledim.

<a id="urun-yapilarini-listeye-ekleme"></a>
## 4. Ürün yapılarını listeye ekleme.

Oluşturduğum liste yapısını, RecyclerView içerisine ekleyebilmem için bu yapıların
kontrolünü sağlayan bir adapter'e ihtiyacım var.
Adapter'in bunu sağlayabilmesi için liste yapısının bir model sınıfına ihtiyacım var
bunu oluşturduktan sonra, model'de ihtiyacım olan verileri belirttiğimde bunları
oluşturduğum liste yapısında, mevcut elementler ile model'in verilerini eşitledim.

Böylece, adapter'e model olarak verdiğim ürün verilerini, RecyclerView'de oluşturduğum
liste yapısı şeklinde görüntülenmesini sağladım.

<a id="urunleri-fiyatlarina-gore-listeleme"></a>
## 5. Ürünleri fiyatlarına göre listeleme

### 5.1
Oluşturduğum Adapter yapısının mevcut listesinin değiştirilebilmesi için Filterable
interface'ini adapter sınıfıma uyguladım.

Bununla birlikte listede istediğimiz ürünü çıkarabilir, ekleyebilir, ya da 
listedeki konumunu değiştirebilirim.

### 5.2

Artık adapter'imin listesini istediğim gibi değiştirebildiğime göre
ürünlerimizi fiyatlarına göre sıralayabiliriz. Bunun için kotlin'de built-in sort
fonksiyonu kullanmak yerine, bir sıralama algoritması kullanmak istedim.

Bunun nedeni, normalde alışık olduğum bubble-sort gibi algoritmaların sonuç vermesi,
mevcut projede kullandığımdan ve "java.util.Arrays" sınıfının
sort fonksiyonundan neden daha yavaş olduğunu anlamak istemem.

Projede, Time Complexity'si built-in sort ile aynı olan QuickSort algoritmasını
kullandım. Bu algoritma daha çok büyük veri setlerinde verimli olduğu halde,
projede mevcut ürün miktarı sürekli olarak artabileceğinden benim için
diğerlerinden daha mantıklıydı.

Algoritma'nın çalışma mantığı;
Dizide bir "pivot" noktası belirleyip, bu benim oluşturduğum ilk durumda 
dizinin sonundaki sayı, bu sayının dizide kendisinden küçük olan sayıları
bu sayıdan önce, kendisinden büyük olan sayıları ise bu sayıdan sonra
olacak şekilde yerlerini değiştirmek.

Bunun için dizinin kendisi, bir başlangıç noktası ve bir bitiş noktası, argumanlarını
alan bir fonksiyon oluşturup, bu fonksiyonda pivot noktasını dizinin son elemanı alıp,
bir index noktası oluşturup bu noktanın başlangıç noktasının bir gerisinden gelmesini sağlamak üzere
başlangıç noktasının bir eksiği olacak şekilde alarak.

Tüm diziyi bir döngüye alıp, döngüdeki her durumda mevcut elamanın
pivot noktası olarak belirlediğimiz sayı ile karşılaştırıp,
eğer döngüde mevcut durumda bulunan eleman, pivot sayısından daha küçük ise index'imizi bir öne alıp,
index'imizin dizide temsil ettiği eleman ile mevcut durumdaki elemanın yerini değiştiriyoruz.

Böylece, eğer döngüdeki mevcut durumdaki eleman eğer pivot sayısından büyük ise, index'imiz artmayacaktır
bu durumda index'in son temsil ettiği elemandan sonraki elemanın pivot sayısından
daha büyük bir sayı olması gerekmekte.

Bu durumda bir sonraki sayının pivot sayısından
küçük olması durumunda, index değerini bir artırıp, index değerinin dizide temsil
ettiği eleman ile mevcut döngüdeki elemanın yerini değiştiriyoruz.

Böylece, küçük olan sayılar, büyük olanların gerisine gelecek şekilde yer değiştiriyor.

Bu işlemi döngü bitene kadar yaptıktan sonra, eğer dizide pivot sayısından
büyük bir sayı bulunuyor ise index değeri bu elemanın bir öncesindeki elemanı
temsil ediyor olacaktır.

Bu durumda, index'imizin bir fazlasını alıp, bu değerin
dizide temsil ettiği eleman ile pivot elemanımızın yerini değiştiriyoruz böylece
pivot elemanından büyük olan sayı ya da sayılar pivottan sonra gelecek şekilde sıralanıyor.

Diziyi tamamen büyükten küçüğe sıralamak için bu işlemi rekürsif bir şekilde,
en son alınan pivot noktasının öncesi ve sonrası olmak üzere, öncesi için
dizinin ilk elemanını başlangıç noktası, en son alınan pivot noktasını bitiş noktası
alarak, mevcut durumdaki pivot noktasının sonrası için, mevcut pivot noktasından
bir sonraki elemanı başlangıç noktası, dizinin son elemanını bitiş noktası alarak
pivot noktaları dizinin sonu ve başına gelene kadar tekrarlıyoruz.

Bu işlemler
sonucunda dizimiz büyükten küçüğe sıralanmış olmakta.

<a id="kullanici-girisi"></a>
## 6. Kullanıcı girişi

Ürünlerimizi listelemeleyi ve düzenlemeyi yaptığımıza göre, artık ürünlerimizi
ekleyebiliriz ancak ürünleri uygulamayı kullanan her kullanıcının veritabanına
ekleyememesi için bir giriş sistemi yapmaya karar verdim. Böylece giriş yapmış
kullanıcılar veritabanına ürünleri istedikleri gibi eklerken, giriş yapmamış
kullanıcılar sadece listelenmiş ürünleri görebilecek.

Kullanıcı girişi için, Firebase tarafından sağlanan FirebaseAuth'u kullanarak,
e-mail ve şifre inputları ile kullanıcı'nın giriş yapabilmesini sağladım.

<a id="urunlerin-veritabanina-kayit-edilmesi"></a>
## 7. Ürünlerin veritabanı'na kayıt edilmesi

Ürünleri kayıt etmek için Firebase tarafından sağlanan Firestore veritabanını
kullandım.

<a id="urunlerin-listeye-eklenmesi"></a>
## 8. Ürünlerin listeye eklenmesi

Firestore veritabanı'na eklediğimiz ürünleri adapter'de bulunan listemiz
ile eşleştirdim.

<a id="aktiviteler-arasi-gecis-ile-uygulamanin-tamamlanmasi"></a>
## 9. Aktiviteler arası geçiş ile uygulamanın tamamlanması

Oluşturduğumuz aktiviteleri butonlar aracılığıyla birbiriyle ilişkilendirip
aktiviteler arası geçişi sağladıktan sonra uygulamam tamamlandı.

> Sunum linki: [Ürün Kayıt Sistemi](https://bandirmaedutr-my.sharepoint.com/:p:/g/personal/berkesongur_ogr_bandirma_edu_tr/EeC8RUKSdolKqO41zBMWkyQB9zqhwQr-ksBmj4IUB4H5vA?e=DDUSf2)
