# 🛍️ E-Commerce Android Application

## 📱 Proje Hakkında

Bu proje, modern Android geliştirme teknolojileri kullanılarak geliştirilmiş kapsamlı bir e-ticaret uygulamasıdır. Uygulama, kullanıcıların ürünleri görüntülemesi, sepet yönetimi, favori ürünler ve ödeme işlemleri gibi temel e-ticaret işlevlerini sağlar.

**Proje Amacı**: Login/Register, ürün listesi, favoriler, sepet, profil ve tema desteği olan bir e-ticaret deneyimi ve öğrenilen her şeyi birleştiren bir e-ticaret uygulaması geliştirmek.

**Proje Adı**: ECommerceApp


### 📚 Temel Konular (Core Topics)
- **Android Development Fundamentals**
  - Activity lifecycle management
  - Fragment navigation
  - UI/UX design principles
  - Material Design implementation

- **Modern Android Architecture**
  - MVVM (Model-View-ViewModel) pattern
  - Repository pattern
  - LiveData and ViewModel implementation
  - Data binding

- **Kotlin Programming**
  - Kotlin syntax and features
  - Coroutines for asynchronous programming
  - Extension functions
  - Null safety

### 🔧 Teknik Konular (Technical Topics)
- **Network Operations**
  - Retrofit for REST API calls
  - HTTP client implementation
  - JSON parsing and serialization
  - Error handling

- **Data Management**
  - Room database for local storage
  - SharedPreferences for user settings
  - Data caching strategies
  - Offline functionality

- **UI/UX Improvements**
  - Responsive design
  - Dark/Light theme support
  - Smooth animations
  - Accessibility features

## 🏗️ Proje Yapısı

```
com.example.ecommerceapp/
├── data/                           # Veri modelleri ve varlıkları
│   ├── CardInfo.kt                # Kredi kartı bilgileri
│   ├── CartItem.kt                # Alışveriş sepeti öğesi
│   ├── LoginResponse.kt           # Kimlik doğrulama yanıtı
│   ├── PaymentData.kt             # Ödeme bilgileri
│   ├── PaymentResponse.kt         # Ödeme API yanıtı
│   ├── PaymentStatus.kt           # Ödeme durumu enum'u
│   ├── Product.kt                 # Ürün veri modeli
│   ├── ProductResponse.kt         # Ürün API yanıtı
│   ├── ThemePreferences.kt        # Tema ayarları
│   └── User.kt                    # Kullanıcı veri modeli
├── network/                        # Ağ katmanı
│   ├── ApiService.kt              # REST API arayüzü
│   └── RetrofitClient.kt          # HTTP client yapılandırması
├── repository/                     # Veri depoları
│   └── ProductRepository.kt       # Ürün veri işlemleri
├── screens/                        # UI ekranları
│   ├── CartScreen.kt              # Alışveriş sepeti
│   ├── FavoritesScreen.kt         # Favori ürünler
│   ├── LoginScreen.kt             # Kullanıcı kimlik doğrulama
│   ├── PaymentScreen.kt           # Ödeme işlemi
│   ├── ProductDetailScreen.kt     # Ürün bilgileri
│   ├── ProductListScreen.kt       # Ürün kataloğu
│   └── ProfileScreen.kt           # Kullanıcı profili
├── ui.theme/                       # UI tema yapılandırması
├── viewmodel/                      # ViewModels
│   ├── MainViewModel.kt           # Ana uygulama mantığı
│   └── MainViewModelFactory.kt    # ViewModel fabrikası
└── MainActivity.kt                 # Ana uygulama giriş noktası
```

## ✨ Özellikler

### 🎯 Proje Amacı
E-ticaret deneyimi ve öğrenilen tüm teknolojileri birleştiren kapsamlı bir e-ticaret uygulaması geliştirmek. Login/Register, ürün listesi, favoriler, sepet, profil ve tema desteği ile tam bir e-ticaret deneyimi sunmak.

### 🔐 Kullanıcı Yönetimi
- **Giriş/Kayıt Sistemi**: DummyJson API ile güvenli kimlik doğrulama
- **Profil Yönetimi**: Kullanıcı bilgileri, logout ve tema değişimi
- **Oturum Yönetimi**: Güvenli kullanıcı oturumu

### 🛍️ Ürün Yönetimi
- **Ürün Listesi**: Tüm ürünleri görüntüleme ve arama
- **Ürün Detayı**: Detaylı ürün bilgileri ve özellikler
- **Ürün Arama**: Gerçek zamanlı ürün arama ve filtreleme
- **Ürün Kategorileri**: Organize edilmiş ürün grupları

### 🛒 Alışveriş Sepeti
- **Sepete Ekleme**: Ürünleri sepete ekleme ve miktar ayarlama
- **Sepet Yönetimi**: Ürün ekleme/çıkarma ve miktar güncelleme
- **Sipariş Özeti**: Ürün sayısı, ara toplam, kargo ve toplam hesaplama
- **Ödeme Süreci**: Güvenli ödeme işlemi

### ❤️ Favori Ürünler
- **Favori Ekleme**: Beğenilen ürünleri favorilere ekleme
- **Favori Listesi**: Kişiselleştirilmiş favori ürün koleksiyonu
- **Çevrimdışı Destek**: Offline erişim ve senkronizasyon
- **Hızlı Erişim**: Favori ürünlere kolay erişim

### 💳 Ödeme İşlemleri
- **Güvenli Ödeme**: Kredi kartı ve kapıda ödeme seçenekleri
- **Kart Bilgileri**: Güvenli kart bilgisi girişi
- **Teslimat Adresi**: Teslimat bilgileri yönetimi
- **Ödeme Takibi**: Ödeme durumu ve onay süreci

### 🌙 Tema Desteği
- **Dinamik Tema**: Açık/koyu tema arasında geçiş
- **Kullanıcı Tercihleri**: Kişiselleştirilmiş tema ayarları
- **Material Design 3**: Modern ve tutarlı tasarım dili
- **Responsive UI**: Farklı ekran boyutlarına uyum

### 🔄 Offline Özellikler
- **Veri Önbelleği**: Çevrimdışı ürün erişimi
- **Senkronizasyon**: Online/offline veri senkronizasyonu
- **Yerel Depolama**: Room Database ile veri saklama
- **Offline Favoriler**: Çevrimdışı favori ürün yönetimi

## 🛠️ Kullanılan Teknolojiler

### 📱 Android & UI
- **Kotlin**: Ana programlama dili
- **Jetpack Compose**: Modern UI geliştirme toolkit'i
- **Material Design 3**: En son tasarım sistemi
- **Navigation Compose**: Ekranlar arası geçiş yönetimi

### 🌐 Network & API
- **Retrofit**: REST API çağrıları için HTTP client
- **OkHttp**: HTTP client kütüphanesi
- **Gson**: JSON serialization/deserialization
- **Dummy API'ler**:
  - **JSONPlaceholder**: Kullanıcı, post, albüm test verileri
  - **ReqRes**: Giriş ve kayıt işlemleri
  - **DummyJSON**: Ürün listesi, arama ve kimlik doğrulama

### 💾 Data Storage & Management
- **Room Database**: Yerel veritabanı
- **SharedPreferences**: Kullanıcı ayarları
- **DataStore**: Modern preferences storage
- **Offline Senkronizasyon**: Çevrimdışı veri yönetimi

### 🏗️ Architecture & Patterns
- **MVVM**: Model-View-ViewModel mimarisi
- **Repository Pattern**: Veri kaynak yönetimi
- **Dependency Injection**: Hilt ile bağımlılık enjeksiyonu
- **Coroutines**: Asenkron programlama

### 🔧 Development Tools
- **Android Studio**: IDE
- **Gradle**: Build automation
- **Git**: Version control
- **Material Design 3**: UI/UX framework

## 📊 Mimari

### MVVM Pattern
```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│    View     │◄──►│  ViewModel  │◄──►│  Repository │
│  (UI)      │    │ (Business   │    │ (Data)      │
└─────────────┘    │   Logic)    │    └─────────────┘
                   └─────────────┘
```

### Repository Pattern
- **Data Layer**: API çağrıları ve yerel depolama
- **Repository**: Veri kaynağı soyutlaması
- **ViewModel**: İş mantığı ve UI durumu
- **View**: UI bileşenleri ve kullanıcı etkileşimi

## 🔒 Güvenlik

- **HTTPS**: Güvenli ağ iletişimi
- **Token-based Authentication**: JWT uygulaması
- **Data Encryption**: Hassas veri koruması
- **Input Validation**: Kullanıcı girişi doğrulaması

### 🎨 Uygulama Arayüzü

#### 🔐 Giriş/Kayıt Ekranı
- **E-Shop Logo**: Mor dairesel logo ile "E-Shop" yazısı
- **Hoş Geldiniz Mesajı**: "E-Commerce App'e Hoş Geldiniz!"
- **Giriş Formu**: Kullanıcı adı ve şifre alanları
- **Giriş Butonu**: "GİRİŞ YAP" butonu
- **Yardımcı Linkler**: Şifre hatırlatma ve kayıt olma seçenekleri

![Giriş/Kayıt Ekranı](screenshots/login_register_screen.jpg)

#### 🛍️ Ürün Listesi
- **App Bar**: "ShopApp" başlığı ve arama ikonu
- **Arama Çubuğu**: "Ürün ara..." placeholder'ı ile arama
- **Ürün Kartları**: iPhone 12 Pro, MacBook Pro, AirPods Pro, Apple Watch, iPad Air
- **Ürün Bilgileri**: Fiyat, puan, stok durumu
- **Alt Navigasyon**: Ana sayfa, Favoriler, Sepet, Profil

![Ürün Listesi Ekranı](screenshots/product_list_screen.jpg)

#### 👤 Profil Ekranı
- **Kullanıcı Bilgileri**: emilys kullanıcı adı ve email
- **Tema Seçimi**: "Karanlık Tema" toggle switch'i
- **Çıkış Butonu**: "Çıkış Yap" butonu
- **Navigasyon**: Profil sekmesi aktif

![Profil Ekranı](screenshots/profile_screen.jpg)

#### 🛒 Sepet Ekranı
- **Sepet Başlığı**: "Sepetim" başlığı ve sepet ikonu
- **Ürün Detayları**: iPad Air - 2 adet, $1,198.00 toplam
- **Miktar Seçici**: "-" ve "+" butonları ile miktar ayarlama
- **Sipariş Özeti**: Ürün sayısı, ara toplam, kargo, toplam
- **Ödeme Butonu**: "ÖDEMEYİ TAMAMLA" butonu

![Sepet Ekranı](screenshots/cart_screen.jpg)

#### ❤️ Favoriler Ekranı
- **Favori Başlığı**: "Favorilerim" başlığı ve kalp ikonu
- **Ürün Kartı**: MacBook Pro - $1,199.00, 4.6/5 puan
- **Favori İkonu**: Kırmızı kalp ikonu
- **Sepete Ekle**: Mor "Sepete Ekle" butonu

![Favoriler Ekranı](screenshots/favorites_screen.jpg)

#### 📱 Ürün Detay Ekranı
- **Ürün Başlığı**: "AirPods Pro" ve geri ok
- **Ürün Bilgileri**: $249.0 fiyat, 4.6/5 puan
- **Ürün Açıklaması**: "Active Noise Cancellation, Transparency mode, and spatial audio"
- **Miktar Seçici**: Miktar ayarlama butonları
- **Sepete Ekle**: "SEPETE EKLE (1 adet)" butonu
- **Özellikler**: Ücretsiz kargo ve garanti bilgileri

![Ürün Detay Ekranı](screenshots/product_detail_screen.jpg)

#### 💳 Ödeme Ekranı
- **Ödeme Başlığı**: "Ödeme" başlığı ve $1,199.00 toplam
- **Ödeme Yöntemi**: Kredi kartı ve kapıda ödeme seçenekleri
- **Kart Bilgileri**: Kart numarası, M/YY, CVV, kart sahibi
- **Teslimat Adresi**: Adres, şehir, posta kodu alanları

![Ödeme Ekranı](screenshots/payment_screen.jpg)

### 🎯 Uygulama Akışı
1. **Giriş**: Login ekranından kullanıcı kimlik doğrulama
2. **Ürün Keşfi**: Ürün listesi ve arama ile ürün bulma
3. **Favori Ekleme**: Beğenilen ürünleri favorilere ekleme
4. **Sepete Ekleme**: Ürünleri sepete ekleme ve miktar ayarlama
5. **Profil Yönetimi**: Kullanıcı bilgileri ve tema ayarları
6. **Ödeme Süreci**: Sepet özeti ve güvenli ödeme işlemi

### 🌟 Tasarım Özellikleri
- **Material Design 3**: Modern ve tutarlı tasarım dili
- **Mor Renk Paleti**: E-Shop marka kimliği
- **Responsive Layout**: Farklı ekran boyutlarına uyum
- **Dark/Light Theme**: Kullanıcı tercihine göre tema değişimi
- **Smooth Navigation**: Akıcı sayfa geçişleri
- **Intuitive UI**: Kullanıcı dostu arayüz tasarımı

## 🧪 Test

### Test Türleri
- **Unit Tests**: İş mantığı testi
- **Integration Tests**: Bileşen etkileşim testi
- **UI Tests**: Kullanıcı arayüzü testi
- **Performance Tests**: Uygulama performans testi

## 🎓 Öğrenilen Katkılar

### 🚀 Jetpack Compose ile Modern UI Geliştirme
- **Declarative UI**: XML tabanlı arayüz geliştirmeden farklı olarak, deklaratif yaklaşımla modern ve dinamik kullanıcı arayüzleri oluşturma
- **Reactive UI**: UI kodunu daha kısa, anlaşılır ve reaktif hale getirme
- **Modern Toolkit**: Jetpack Compose ile çağdaş Android UI geliştirme

### 🌐 Retrofit ile API Yönetimi
- **API Servisleri**: Farklı API servislerinden veri çekme ve yönetme becerisi
- **Veri Senkronizasyonu**: Çevrimdışı önbellek ile veri senkronizasyonu
- **Network Layer**: Güvenilir ağ katmanı oluşturma

### 🏗️ MVVM Mimarisi Uygulaması
- **Separation of Concerns**: İş mantığını kullanıcı arayüzünden tamamen ayırma
- **Testability**: Kodun test edilebilirliğini artırma
- **Maintainability**: Sürdürülebilir kod yapısı oluşturma

### 🎨 Material Design 3 Entegrasyonu
- **Tema Desteği**: Koyu/açık tema desteği gibi özellikleri kolayca uygulama
- **Estetik UI**: Kullanıcı arayüzünün estetik ve tutarlı olmasını sağlama
- **Design System**: Modern tasarım sistemi kullanımı

### 🧭 Navigation ve Deep Linking
- **Route Management**: Her ekran için benzersiz yollar (routes) tanımlama
- **Page Transitions**: Sayfalar arası geçiş yapma becerisi
- **Deep Linking**: Uygulama dışından belirli sayfalara doğrudan yönlendirme

## 📈 Performans

### Optimizasyonlar
- **Lazy Loading**: Verimli veri yükleme
- **Image Caching**: Optimize edilmiş görsel işleme
- **Database Indexing**: Hızlı veri erişimi
- **Memory Management**: Verimli kaynak kullanımı


## 📚 Kaynaklar

### 🔗 Resmi Dokümantasyon
- [Jetpack Compose UI App Development Toolkit](https://developer.android.com/jetpack/compose) - Android Developers
- [Kotlin Documentation](https://kotlinlang.org/docs/) - Kotlin Docs
- [Get Started with Android](https://developer.android.com/training/basics/firstapp) - Android Developers

### 🌐 API Servisleri
- [DummyJSON](https://dummyjson.com/) - Ücretsiz Fake REST API
- [JSONPlaceholder](https://jsonplaceholder.typicode.com/) - Ücretsiz Fake REST API
- [ReqRes](https://reqres.in/) - Giriş ve Kayıt için Test API

### 📖 Öğrenme Kaynakları
- Android Developer Community
- Material Design Team
- Open Source Contributors
- Online Tutorials and Courses


- **Android Developer Community**: Sürekli destek ve rehberlik
- **Material Design Team**: Modern tasarım sistemi
- **Open Source Contributors**: Topluluk katkıları
- **Learning Resources**: Eğitim materyalleri ve dokümantasyon

---

