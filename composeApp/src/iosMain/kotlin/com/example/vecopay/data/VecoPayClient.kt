//package com.example.vecopay.data
//
//import io.github.jan.supabase.createSupabaseClient
//import io.github.jan.supabase.auth.Auth
//import io.github.jan.supabase.postgrest.Postgrest
//import io.github.jan.supabase.storage.Storage
//import io.github.jan.supabase.serializer.KotlinXSerializer
//import kotlinx.serialization.json.Json
//
//object VecoPayClient {
//
//    private const val SUPABASE_URL = "https://thtkawwauhxqdizsuvuk.supabase.co"
//    private const val SUPABASE_KEY = "sb_publishable_wCIoihENjg_oRdt-kWaZug_bS9hvdAI"
//
//    val client = createSupabaseClient(
//        supabaseUrl = SUPABASE_URL,
//        supabaseKey = SUPABASE_KEY
//    ) {
//        // Configuración correcta para Supabase v3
//        defaultSerializer = KotlinXSerializer(Json {
//            ignoreUnknownKeys = true
//            prettyPrint = true
//            encodeDefaults = true
//        })
//
//        install(Auth)
//        install(Postgrest)
//        install(Storage)
//    }
//}