package com.moodtrip.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SpotRepository {
    static List<String> moodTags() {
        return Arrays.asList(
                "healing",
                "tenang",
                "pedas",
                "romantis",
                "aesthetic",
                "hidden gem",
                "murah",
                "keluarga"
        );
    }

    static List<TravelSpot> all() {
        List<TravelSpot> spots = new ArrayList<>();
        spots.add(new TravelSpot(
                1,
                "Islamic Center NTB",
                "Landmark Kota",
                "Mataram",
                "Landmark ikonik Mataram dengan area luas dan arsitektur megah.",
                "Cocok untuk wisata religi, foto arsitektur, dan mengenal identitas kota.",
                "Gratis",
                "08.00 - 21.00",
                4.7f,
                0.72f,
                0.24f,
                "landmark", "aesthetic", "budaya", "keluarga", "murah", "foto-foto"
        ));
        spots.add(new TravelSpot(
                2,
                "Taman Sangkareang",
                "Ruang Publik",
                "Pusat Kota Mataram",
                "Taman kota untuk jalan santai, olahraga ringan, dan berkumpul sore hari.",
                "Tempat murah dan mudah dijangkau untuk healing singkat di tengah kota.",
                "Gratis",
                "05.30 - 22.00",
                4.3f,
                0.36f,
                0.58f,
                "healing", "tenang", "keluarga", "murah", "jalan santai", "kota"
        ));
        spots.add(new TravelSpot(
                3,
                "Pantai Senggigi",
                "Wisata Pantai",
                "Lombok Barat",
                "Pantai populer dengan garis pantai panjang, sunset, dan pilihan kuliner sekitar.",
                "Spot sunset klasik Lombok yang cocok untuk pasangan dan keluarga.",
                "Rp10.000 - Rp30.000",
                "06.00 - 19.00",
                4.6f,
                0.47f,
                0.35f,
                "sunset", "romantis", "pantai", "keluarga", "foto-foto", "populer"
        ));
        spots.add(new TravelSpot(
                4,
                "Bukit Merese",
                "Wisata Alam",
                "Kuta Lombok",
                "Bukit savana dengan pemandangan laut dan jalur naik yang relatif ringan.",
                "View luas untuk healing, foto, dan menikmati angin pantai.",
                "Rp10.000 - Rp25.000",
                "05.30 - 18.30",
                4.8f,
                0.61f,
                0.68f,
                "healing", "sunset", "aesthetic", "adventurous", "foto-foto", "nature"
        ));
        spots.add(new TravelSpot(
                5,
                "Desa Sade",
                "Wisata Budaya",
                "Lombok Tengah",
                "Desa adat Sasak yang menampilkan rumah tradisional, kain tenun, dan budaya lokal.",
                "Tempat belajar budaya Sasak dan melihat speciality lokal Lombok.",
                "Donasi / paket lokal",
                "08.00 - 17.00",
                4.4f,
                0.21f,
                0.72f,
                "budaya", "lokal banget", "keluarga", "aesthetic", "belajar budaya", "hidden gem"
        ));
        spots.add(new TravelSpot(
                6,
                "Warung Ayam Taliwang",
                "Kuliner",
                "Mataram",
                "Kuliner khas Lombok dengan ayam bakar berbumbu kuat dan pilihan level pedas.",
                "Speciality pedas lokal yang wajib diuji untuk food hunter.",
                "Rp25.000 - Rp75.000",
                "10.00 - 22.00",
                4.6f,
                0.18f,
                0.28f,
                "pedas", "kuliner lokal", "halal", "food hunter", "populer", "malam"
        ));
        spots.add(new TravelSpot(
                7,
                "Nasi Balap Puyung",
                "Kuliner",
                "Mataram / Lombok Tengah",
                "Nasi khas Lombok dengan lauk ayam suwir pedas dan bumbu gurih.",
                "Cocok untuk pencarian makanan pedas, cepat, dan lokal banget.",
                "Rp15.000 - Rp35.000",
                "08.00 - 21.00",
                4.5f,
                0.82f,
                0.63f,
                "pedas", "murah", "kuliner lokal", "halal", "cepat", "hidden gem"
        ));
        spots.add(new TravelSpot(
                8,
                "Kafe Ampenan Heritage",
                "Kafe",
                "Kota Tua Ampenan",
                "Kafe bernuansa heritage di kawasan tua dekat pesisir Ampenan.",
                "Cocok untuk nongkrong tenang, foto, dan eksplor suasana kota lama.",
                "Rp25.000 - Rp75.000",
                "10.00 - 22.00",
                4.2f,
                0.55f,
                0.18f,
                "aesthetic", "cozy", "tenang", "hidden gem", "nongkrong", "foto-foto"
        ));
        return spots;
    }
}
