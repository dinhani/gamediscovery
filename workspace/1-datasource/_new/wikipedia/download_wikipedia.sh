#!/bin/bash

download_category(){
    echo "========================================================================="
    echo $1
    echo "========================================================================="

    # get list of games that belongs to a category
    local games=$(curl -d "" "https://en.wikipedia.org/w/index.php?title=Special:Export&addcat&catname=$1" \
        | grep multiline \
        | awk 'match($0, /"name":"pages","value":"(.+)\",/, arr) {print arr[1]}')

    # urlencode
    local gamesEncoded=$(echo $games \
        | perl -pe 's/\\n/%0A/g' \
        | perl -pe "s/&#039;/'/g" \
        | perl -pe "s/&amp;/%26/g" )

    # download all games of the category
    curl -d "&pages=$gamesEncoded&curonly=1&action=submit" "https://en.wikipedia.org/w/index.php?title=Special:Export" > $GD_DATA_RAW/wikipedia/$1.xml
}

# 1st / 2nd generation
download_category "Atari_2600_games"

# 3rd generation
download_category "Atari_7800_games"
download_category "Master_System_games"
download_category "Nintendo_Entertainment_System_games"

# 4th generation
download_category "CD-i_games"
download_category "Neo_Geo_CD_games"
download_category "Neo_Geo_games"
download_category "Sega_32X_games"
download_category "Sega_CD_games"
download_category "Sega_Genesis_games"
download_category "Sega_Meganet_games"
download_category "Super_Nintendo_Entertainment_System_games"
download_category "SuperGrafx_games"
download_category "TurboGrafx-16_games"
download_category "TurboGrafx-CD_game"

# 5th generation
download_category "3DO_Interactive_Multiplayer_games"
download_category "Apple_Bandai_Pippin_games"
download_category "Atari_Jaguar_games"
download_category "CD32_games"
download_category "Game_Boy_Color_games"
download_category "Game.com_games"
download_category "Neo_Geo_CD_games"
download_category "Nintendo_64_games"
download_category "PC-FX_games"
download_category "PlayStation_(console)_games"
download_category "Sega_Saturn_games"

# 6th generation
download_category "Dreamcast_games"
download_category "Game_Boy_Advance_games"
download_category "GameCube_games"
download_category "N-Gage_games"
download_category "Neo_Geo_Pocket_Color_games"
download_category "PlayStation_2_games"
download_category "WonderSwan_Color_games"
download_category "WonderSwan_games"
download_category "Xbox_games"

# 7th generation
download_category "Gizmondo_games"
download_category "GP2X_games"
download_category "N-Gage_service_games"
download_category "Nintendo_DS_games"
download_category "PlayStation_3_games"
download_category "PlayStation_Portable_games"
download_category "Wii_games"
download_category "Xbox_360_games"
download_category "Zeebo_games"

# 8th generation
download_category "GameStick_Games"
download_category "Nintendo_3DS_games"
download_category "Nintendo_Switch_games"
download_category "Ouya_games"
download_category "PlayStation_4_games"
download_category "PlayStation_Vita_games"
download_category "Wii_U_games"
download_category "Xbox_One_games"

# PC generation
download_category "Windows_games"
download_category "Linux_games"

# Mobile generation
download_category "Android_(operating_system)_games"
download_category "IOS_games"
download_category "Windows_Phone_games"
