<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div id="search-container" ng-controller="SearchDesktopController" ng-init="initialize()">

    <!-- INDEX PAGE WRAPPER -->
    <div id="search-main-search-wrapper" >
        <div class="ui one column container grid" ng-show="data.filters.length === 0">
            <div  class="column">

                <!-- =================================================================== -->
                <!-- MAIN SEARCH -->
                <!-- =================================================================== -->
                <div id="search-main-search">

                    <!-- HEADER -->
                    <div class="ui top attached big header" translate>
                        Game search
                    </div>

                    <!-- SEARCH INPUT -->
                    <div class="ui bottom attached segment">
                        <div id="search-main-search-input" class="ui fluid {{data.isSearchingConceptEntries ? 'loading' : ''}} search">
                            <div class="ui left icon large input">
                                <!-- ICON -->
                                <i class="search icon "></i>

                                <!-- INPUT -->
                                <input class="prompt big text" placeholder="{{'Search by genre, theme, platform, company, release year and more'| translate}}..."
                                       ng-model="data.mainSearchText" ng-change="executeMainSearch()" ng-focus="executeMainSearch()" ng-keypress="executeMainSearchCheckEnter($event)"
                                       ng-model-options="{debounce: 200}" ng-trim="false"
                                       analytics-on="focus" analytics-category="Search" analytics-event="Focus on main search">
                            </div>
                            <div class="results"></div>
                        </div>

                        <div id="search-main-search-examples">
                            <div class="item small text"><span translate>e.g.</span>:</div>
                            <div class="item small text"><a href="/recommendations?q=game-the-witcher-3-wild-hunt"      ng-click="addFilterFromExample({uid: 'game-the-witcher-3-wild-hunt', name: 'The Witcher 3: Wild Hunt'}, $event)">the witcher 3</a></div>
                            <div class="item small text"><a href="/recommendations?q=platform-playstation-4"            ng-click="addFilterFromExample({uid: 'platform-playstation-4', name: 'PlayStation  4'}, $event)">playstation 4</a></div>
                            <div class="item small text"><a href="/recommendations?q=gamemode-single-player"            ng-click="addFilterFromExample({uid: 'gamemode-single-player', name: 'Single Player'}, $event)">single player</a></div>
                            <div class="item small text"><a href="/recommendations?q=genre-open-world"                  ng-click="addFilterFromExample({uid: 'genre-open-world', name: 'Open World'}, $event)">open world</a></div>
                            <div class="item small text"><a href="/recommendations?q=theme-fantasy"                     ng-click="addFilterFromExample({uid: 'theme-fantasy', name: 'Fantasy'}, $event)">fantasy</a></div>
                            <div class="item small text"><a href="/recommendations?q=mechanics-magic"                   ng-click="addFilterFromExample({uid: 'mechanics-magic', name: 'Magic'}, $event)">magic</a></div>
                            <div class="item small text"><a href="/recommendations?q=creature-dragon"                   ng-click="addFilterFromExample({uid: 'creature-dragon', name: 'Dragon'}, $event)">dragon</a></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- =================================================================== -->
    <!-- =================================================================== -->

    <!-- SEARCH RESULTS WRAPPER -->
    <div class="ui one column container grid ng-hide" ng-show="data.filters.length > 0" ng-cloak>

        <!-- =================================================================== -->
        <!-- FILTERS -->
        <!-- =================================================================== -->
        <div id="search-filters" class="column">
            <!-- HEADER -->
            <div class="ui top attached big header" translate>
                Game search
            </div>

            <!-- FILTERS -->
            <div class="ui bottom attached segment">

                <!-- FIELDS -->
                <div id="search-filters-wrapper" class="ui three column middle aligned stackable divided grid">
                    <!-- CURRENT FILTERS -->
                    <div class="column search-filter" ng-repeat="filter in data.filters track by $index" >
                        <span class="big text">
                            <span class="search-filter-text">{{filter.name}}</span>
                            <i ng-click="removeFilter(filter, $index)" class="search-filter-icon delete grey icon clickable"></i>
                        </span>
                    </div>

                    <!-- NEW FILTER -->
                    <div class="column">
                        <div id="search-secondary-search" class="ui fluid {{data.isSearchingConceptEntries ? 'loading' : ''}} search medium text">
                            <input class="prompt medium text" type="text" placeholder="{{'Add filter'| translate}}"
                                   ng-model="data.secondarySearchText" ng-change="executeSecondarySearch()" ng-trim="false" ng-focus="executeSecondarySearch()" ng-keypress="executeSecondarySearchCheckEnter($event)"
                                   ng-model-options="{debounce: 200}"
                                   analytics-on="focus" analytics-category="Search" analytics-event="Focus on secondary search">
                            <div class="results"></div>
                        </div>
                    </div>
                </div>               
            </div>
        </div>

        <!-- =================================================================== -->
        <!-- RELATED GAMES -->
        <!-- =================================================================== -->
        <div id="search-results" class="column">

            <!-- HEADER -->
            <h1 class="ui top attached big header" >
                <div class="ui stackable grid">
                    <div class="twelve wide middle aligned column" ng-bind="$root.data.header">
                        Game Discovery
                    </div>
                    <!-- GAMES FOUND -->
                    <div class="four wide middle aligned right aligned column"  ng-show="!data.isSearchBySingleGame && !data.isSearchingGames">
                        <div class="ui small header">{{data.gameSearch.totalOfResults}} <span translate>games found</span></div>
                    </div>
                </div>
            </h1>

            <!-- GAMES -->
            <div id="search-results-games" class="ui bottom attached segment" style="min-height: {{adjustHeight('search-results-games')}};">

                <!-- GAMES -->
                <ol  class="ui four column doubling grid">
                    <li class="column" ng-repeat="game in data.gameSearch.results track by $index">
                        <a href="/recommendations/{{game.game.uid}}" gd-concept-card class="prerender-wait"
                           concept-entry="game.game" index="{{$index + 1}}" show-extra-info="true"
                           ng-click="showGame(game.game, $event)"
                           analytics-on="mouseover" analytics-category="Search" analytics-event="Hover game" analytics-label="{{$index}} - {{game.game.name}}">
                        </a>                            
                    </li>
                </ol>

                <!-- LOAD MORE -->
                <div class="ui stackable grid" ng-show="data.gameSearch !== null && (data.gameSearch.results.length < data.gameSearch.totalOfResults)">
                    <!-- PADDING -->
                    <div class="four wide column desktop-only"></div>

                    <!-- BUTTON -->
                    <div class="eight wide center aligned column">
                        <div ng-click="loadMoreGames()" class="ui big {{'data.isSearchingGames' ? 'disable' : ''}} button" 
                             analytics-on="click" analytics-category="Search" analytics-event="Load more games" analytics-label="{{data.page}}"
                             translate>Load more games</div>                                        
                    </div>
                </div>

                <!-- LOADING -->
                <div class="ui center aligned container small text" ng-show="data.isSearchingGames">                    
                    <br/>
                    <span translate>Loading</span>...
                </div>
            </div>
        </div>

        <!-- =================================================================== -->
        <!-- RELATED FILTERS -->
        <!-- =================================================================== -->
        <div id="search-related-filters" class="column" ng-show="data.gameSearchRecommendedFilters.length > 0 && !data.isSearchingGames">
            <div class="ui top attached big header" translate>
                Related searches
            </div>
            <div  class="ui bottom attached segment" >
                <div class="ui six column doubling grid" >                                            
                    <a class="small text column" ng-click="addFilterFromSuggestion(filter)" ng-repeat="filter in data.gameSearchRecommendedFilters" >
                        {{filter.name.toLowerCase()}}
                    </a>                   
                </div>
            </div>
        </div>
    </div>

    <!-- =================================================================== -->
    <!-- GAME DETAILS -->
    <!-- =================================================================== -->
    <div id="search-details" class="ui modal" style="height: {{$root.data.window.height - 100}}px;">

        <!-- CLOSE -->
        <i class="big close icon"></i>

        <!-- GAME -->
        <div>
            <%@ include file="game.jsp" %>
        </div>

        <!-- ACTIONS -->
        <div class="actions">
            <div class="ui three column stackable middle aligned grid">       

                <!-- PADDING -->
                <div class="column desktop-only"></div>
                <div class="column desktop-only"></div>               
                
                <!-- NEXT GAME -->
                <div class="column">
                    <div ng-click="nextGame()" class="ui right labeled icon button small text">
                        <span translate>Next Game</span>
                        <i class="ui arrow right icon"></i>
                    </div>
                </div>
                
                <!-- CLOSE -->
                <div class="column mobile-only">
                    <div class="ui right labeled icon cancel black button small text">
                        <span translate>Close</span>
                        <i class="ui close icon"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>