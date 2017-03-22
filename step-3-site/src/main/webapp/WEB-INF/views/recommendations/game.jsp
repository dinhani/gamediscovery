<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div>

    <!-- HEADER -->
    <div class="ui top attached segment">
        <h2 class="ui big centered header" ng-cloak>
            <span ng-show="data.selectedGameIndex">{{data.selectedGameIndex + 1}}.</span> {{data.selectedGame.name}}
        </h2>
    </div>

    <!-- GAME INFO -->    
    <div id="game-accordion" class="ui styled fluid accordion desktop-scroll-vertical mobile-no-scroll-vertical" style="max-height: {{adjustHeight('game-accordion', 100)}};">

        <!-- OVERVIEW -->
        <h3 id="game-accordion-overview-header" class="ui small header title"
            analytics-on="click" analytics-category="Game" analytics-event="Toggle accordion" analytics-label="Overview">
            <i class="dropdown icon"></i> {{'Overview'| translate }}
        </h3>
        <div id="game-accordion-overview-content" class="content">

            <div class="ui grid top attached segment">
                <!-- COVER -->
                <div id="game-cover" class="
                     four wide widescreen
                     four wide computer
                     sixteen wide mobile
                     column">
                    <img class="ui image" ng-src="{{data.selectedGame.imageFullPath}}" alt="{{data.selectedGame.name}} cover"
                         fallback-src="/app/assets/img/default.png" ng-if="data.selectedGame !== null"/>
                </div>

                <div class="
                     twelve wide widescreen
                     twelve wide computer
                     sixteen wide mobile
                     column">

                    <!-- IMAGES -->
                    <div id="game-images">
                        <div class="small text" translate ng-if="data.isLoadingGame">{{'Loading'| translate}}...</div>
                        <a ng-href="{{data.selectedGameLinks.imagesLink}}" target="_blank"
                           analytics-on="click" analytics-category="Game" analytics-event="View image" analytics-label="{{data.selectedGame.name}}">
                            <img ng-repeat="image in data.selectedGameLinks.images| limitTo:12"
                                 class="ui bordered image" ng-src="{{image}}" alt="{{data.selectedGame.name}} gameplay"/>
                        </a>
                    </div>

                    <!-- LINKS -->
                    <div id="game-links" class="ui horizontal list" style="margin-top: 1rem;">
                        <div class="small text item">
                            <a ng-href="{{data.selectedGameLinks.imagesLink}}" target="_blank"
                               analytics-on="click" analytics-category="Game" analytics-event="Search on Google Images" analytics-label="{{data.selectedGame.name}}" style="width: 100%;">
                                <i class="icon picture"></i><span translate>Google Images</span></a>
                        </div>
                        <div class="small text item">
                            <a ng-href="{{data.selectedGameLinks.youtubeLink}}" target="_blank"
                               analytics-on="click" analytics-category="Game" analytics-event="Search on Youtube" analytics-label="{{data.selectedGame.name}}" style="width: 100%;">
                                <i class="icon youtube"></i>Youtube
                            </a>
                        </div>
                        <div class="small text item">
                            <a ng-href="{{data.selectedGameLinks.hltbLink}}" target="_blank"
                               analytics-on="click" analytics-category="Game" analytics-event="Search on How Long to Beat" analytics-label="{{data.selectedGame.name}}" style="width: 100%;">
                                <i class="icon clock"></i>How Long to Beat
                            </a>
                        </div>
                    </div>
                </div>
            </div>


            <!-- SUMMARY -->
            <div class="ui attached segment" ng-if="data.selectedGame.summary">
                <div id="game-summary" style="max-height: 5rem; overflow: hidden; font-size: 1.1rem; line-height: 1.5;">
                    {{data.selectedGame.summary}}                    
                </div>
                <a href="#"  ng-click="showMoreSummary()" ng-if="data.selectedGameShowMore" class="small text"
                   analytics-on="click" analytics-category="Game" analytics-event="Show more summary">
                    <br/>
                    <span translate>Show more</span>
                </a>
            </div>

            <!-- GENERAL INFORMATION -->            
            <div gd-game-relationship game="data.selectedGame" relationships="['platforms', 'genres', 'releaseYears']" bottom-attached="true"></div>
        </div>

        <!-- GAMEPLAY -->
        <h3 id="game-accordion-gameplay-header" class="ui small header title" 
            analytics-on="click" analytics-category="Game" analytics-event="Toggle accordion" analytics-label="Gameplay">
            <i class="dropdown icon"></i> {{'Gameplay'| translate}}
        </h3>
        <div id="game-accordino-gameplay-content" class="content">
            <span class="small text" translate ng-if="data.isLoadingGame">{{'Loading'| translate}}...</span>
            <div gd-game-relationship game="data.selectedGame" relationships="['mechanics', 'gameModes', 'graphics', 'engines', 'durations']"></div>
        </div>

        <!-- PLOT -->
        <h3 id="game-accordion-plot-header" class="ui small header title"
            analytics-on="click" analytics-category="Game" analytics-event="Toggle accordion" analytics-label="Plot">
            <i class="dropdown icon"></i> {{'Plot'| translate}}
        </h3>
        <div id="game-accordion-plot-content" class="content">
            <span class="small text" translate ng-if="data.isLoadingGame">{{'Loading'| translate}}...</span>
            <div gd-game-relationship game="data.selectedGame" relationships="['series', 'atmospheres', 'themes', 'characters', 'creatures', 'locations', 'periods', 'weapons',  'vehicles', 'sportTeams', 'organizations', 'soundtracks']"></div>
        </div>

        <h3 id="game-accordion-industry-header" class="ui small header title"
            analytics-on="click" analytics-category="Game" analytics-event="Toggle accordion" analytics-label="Industry">
            <i class="dropdown icon"></i> {{'Game Industry'| translate }}
        </h3>
        <div id="game-accordion-industry-content" class="content"
             analytics-on="click" analytics-category="Game" analytics-event="Toggle accordion" analytics-label="{{data.selectedGame.name}}">
            <span class="small text" translate ng-if="data.isLoadingGame">{{'Loading'| translate}}...</span>
            <div gd-game-relationship game="data.selectedGame" relationships="['developerCompanies', 'publisherCompanies', 'developers', 'awards', 'reviews', 'contentClassifications', 'contentCharacteristics']"></div>
        </div>
    </div>
</div>
