package com.example.smartenergy.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Semantic color tokens for SmartEnergy.
 * Use these for domain-specific colors that go beyond Material3 color roles.
 */
object AppColors {

    // ── Status Indicators ──
    val StatusOk = Color(0xFF3FA37C)
    val StatusWarning = Color(0xFFF4B942)
    val StatusError = Color(0xFFD9534F)

    val StatusOkBackground = Color(0xFFE8F5EE)
    val StatusWarningBackground = Color(0xFFFFF8E7)
    val StatusErrorBackground = Color(0xFFFDE8E7)

    // ── Chart / Data Viz ──
    val ChartPrimary = Color(0xFF1E3A5F)
    val ChartSecondary = Color(0xFF3FA37C)
    val ChartTertiary = Color(0xFFF4B942)
    val ChartQuaternary = Color(0xFF7C8DB5)
    val ChartGrid = Color(0xFFE5E7EB)

    // ── Cards ──
    val CardBackground = Color(0xFFFFFFFF)
    val CardElevatedBackground = Color(0xFFFFFFFF)

    // ── Import / Excel ──
    val ExcelGreen = Color(0xFF2E8B57)

    // ── Tags ──
    val TagCurrentBg = Color(0xFFE8F5EE)
    val TagCurrentText = Color(0xFF2E8B57)
    val TagNextBg = Color(0xFFFFF3DB)
    val TagNextText = Color(0xFFBF7B16)
}
