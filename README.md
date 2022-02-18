# Tugas Besar 1 IF2211 Strategi Algoritma 2021/2022
##	Kelompok 47 - Optimum Pride
+ Hansel Valentino Tanoto 13520 046
+ Ziyad Dhia Rafii 13520064
+ Muhammad Naufal Satriandana 13520068

### Tentang Proyek ini
Ini adalah sebuah proyek yang bertujuan untuk menuntaskan tugas mata kuliah Strategi Algoritma. Repository ini berisi source code .Bot sebuah untuk menjalankan permainan balap mobil virtual Overdrive

### Algoritma Greedy
Algoritma Greedy adalah suatu pendekatan pemrograman yang mencari solusi terbaik lokal dengan harapan mencapai solusi terbaik global. Algoritma greedy pada program ini memaksimalkan


Algoritma greedy yang digunakan pada program ini memaksimalkan penggunaan power up boost. Tentunya sebelum itu algoritma ini juga harus bisa menghindari beberapa rintangan seperti wall, mud, oil spill, dan player, dengan urutan prioritas tersebut. Inti dari cara kerja algoritma ini adalah melakukan FIX hingga tidak ada damage pada giliran bot hendak menggunakan command USE_BOOST. Dengan melakukan hal tersebut, penggunaan boost menjadi lebih optimal karena mobil akan melaju sejauh 15 blok daripada hanya 9 blok saja dan dapat bertahan selama 5 ronde jika state map-nya mendukung.  Selain itu program ini juga melakukan berbagai hal agresif seperti menggunakan power up jika tidak ada lagi yang bisa dilakukan. 

### Cara menjalankan Program & Requirement

#### Requiremens
1. Java Development Kit, dapat diunduh [di sini](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
2. IntelliJ, dapat diunduh [di sini](https://www.jetbrains.com/idea/download/#section=windows)
#### Cara Menginstall bot
1. Unduh permainan Overdrive pada link [di sini](https://github.com/EntelectChallenge/2020-Overdrive.)
2. Uduh source code pada repository ini
3. Pindahkan folder src pada repository ke dalam foder starter-packs pada overdrive
4. Edit file game-runner-config.json, pada player-a atau player-b, masukkan directory file bot yang telah diunduh
#### Cara Build Program (lewati jika sudah ter-build)
1. Buka aplikasi IntelliJ lalu buka folder overdrive (starter-packs) yang sudah termasuk bot dalam repository ini
2. Tunggu IntelliJ mendeteksi Maven Project, lalu pada bagian kanan atas terdapat tab Maven
3. Pada bagian lifecycle pilij install lalu double click
#### Cara Run Overdrive
1. Pada root folder terdapat run.bat
2. Double click, dan tunggu match berhasil dijalankan
3. Hasil pertandingan dapat dilihat di match-logs
#### Cara visualisasi hasil pertandingan
1. Untuk memvisualisasikan hasil pertandingan, dapat menggunakan link berikut https://entelect-replay.raezor.co.za/
2. Zip folder hasil pertandingan yang terdapat pada match-logs
3. Lalu pada halaman utama tekan Add A Match


