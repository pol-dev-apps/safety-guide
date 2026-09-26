package dev.pol.safetyguide.data.model

data class EmergencyNumber(
    val number: String,
    val name: String,
    val description: String
)

object EmergencyNumbers {
    val numbers = listOf(
        EmergencyNumber("112", "Numer alarmowy", "Ogólny numer alarmowy"),
        EmergencyNumber("999", "Pogotowie ratunkowe", "Pomoc medyczna"),
        EmergencyNumber("998", "Straż pożarna", "Pożary i ratownictwo"),
        EmergencyNumber("997", "Policja", "Bezpieczeństwo publiczne"),
        EmergencyNumber("994", "Pogotowie wodno-kanalizacyjne", "Awarie wodociągowe"),
        EmergencyNumber("993", "Pogotowie ciepłownicze", "Awarie ogrzewania"),
        EmergencyNumber("992", "Pogotowie gazownicze", "Wycieki gazu"),
        EmergencyNumber("991", "Pogotowie energetyczne", "Awarie prądu"),
        EmergencyNumber("987", "Centrum zarządzania kryzysowego", "Zarządzanie kryzysowe"),
        EmergencyNumber("986", "Straż miejska", "Bezpieczeństwo lokalne"),
        EmergencyNumber("800 70 22 22", "Centrum wsparcia", "Kryzys psychiczny"),
        EmergencyNumber("116 123", "Telefon zaufania", "Dla dorosłych"),
        EmergencyNumber("116 111", "Telefon zaufania", "Dla dzieci i młodzieży"),
        EmergencyNumber("800 12 12 12", "Dziecięcy telefon zaufania", "Rzecznik Praw Dziecka")
    )
}
