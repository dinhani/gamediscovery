app.config(['$translateProvider',
    function ($translateProvider) {

        // TRANSLATIONS
        $translateProvider
                .registerAvailableLanguageKeys(
                        ['en', 'es', 'fr', 'it', 'pt'],
                        {
                            'en_*': 'en',
                            'es_*': 'es',
                            'fr_*': 'fr',
                            'it_*': 'it',
                            'pt_ *': 'pt'
                        })
                .fallbackLanguage('en')
                .useCookieStorage()
                .useSanitizeValueStrategy('escape')
                .determinePreferredLanguage();
    }
]);

// Awards
// Atmospheres
// Characters
// Classifications
// Classifications Characteristics
// Companies
// Creatures
// Developers
// Development Team
// Input Devices
// Game Engines
// Game Mechanics
// Game Modes
// Graphics
// Genres
// Locations
// Medias
// Number of Players
// Periods
// Platforms
// Publishers
// Release Years
// Reviews
// Series
// Soundtracks
// Themes
// Vehicles
// Weapons
