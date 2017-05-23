// =============================================================================
// JS
// =============================================================================
app.controller('GameDetailsController', function ($scope, $rootScope,
    guiService) {
});

// =============================================================================
// HTML
// =============================================================================
app.component("gameDetails", {
    template: `
        <!-- HEADER -->
        <div class="ui top attached segment">
            <h2 class="ui big centered header" ng-cloak>
                <span ng-show="$ctrl.index">{{$ctrl.index + 1}}.</span> {{$ctrl.game.name}}
            </h2>
        </div>

        <!-- CLOSE -->
        <i class="big close icon"></i>

        <!-- GAME INFO -->
        <div id="game" class="ui styled fluid accordion">

            <!-- OVERVIEW -->
            <h3 class="ui small header active title">
                <i class="dropdown icon"></i> {{'Overview'| translate }}
            </h3>
            <div class="content">

                <div class="ui grid top attached segment">
                    <!-- COVER -->
                    <div id="game-cover" class="
                         four wide widescreen
                         four wide computer
                         sixteen wide mobile
                         column">
                        <img class="ui image" ng-src="{{$ctrl.game.imageFullPath}}" alt="{{$ctrl.game.name}} cover" fallback-src="/app/assets/img/default.png" ng-if="$ctrl.game !== null" />
                    </div>

                    <div class="
                         twelve wide widescreen
                         twelve wide computer
                         sixteen wide mobile
                         column">

                        <!-- IMAGES -->
                        <div id="game-images">
                            <div class="small text" translate ng-if="data.isLoadingGame">{{'Loading'| translate}}...</div>
                            <a ng-href="{{$ctrl.gameLinks.imagesLink}}" target="_blank">
                                <img ng-repeat="image in $ctrl.gameLinks.images| limitTo:12" class="ui bordered image" ng-src="{{image}}" alt="{{$ctrl.game.name}} gameplay" />
                            </a>
                        </div>

                        <!-- LINKS -->
                        <div id="game-links" class="ui horizontal list" style="margin-top: 1rem;">
                            <div class="small text item">
                                <a ng-href="{{$ctrl.gameLinks.imagesLink}}" target="_blank"style="width: 100%;">
                                    <i class="icon picture"></i><span translate>Google Images</span></a>
                            </div>
                            <div class="small text item">
                                <a ng-href="{{$ctrl.gameLinks.youtubeLink}}" target="_blank" style="width: 100%;">
                                    <i class="icon youtube"></i>Youtube
                                </a>
                            </div>
                            <div class="small text item">
                                <a ng-href="{{$ctrl.gameLinks.hltbLink}}" target="_blank" style="width: 100%;">
                                    <i class="icon clock"></i>How Long to Beat
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- SUMMARY -->
                <div class="ui attached segment" ng-if="$ctrl.game.summary">
                    <div id="game-summary" style="max-height: 5rem; overflow: hidden; font-size: 1.1rem; line-height: 1.5;">
                        {{$ctrl.game.summary}}
                    </div>
                    <a href="#" ng-click="showMoreSummary()" ng-if="$ctrl.gameShowMore" class="small text">
                        <br/>
                        <span translate>Show more</span>
                    </a>
                </div>

                <!-- GENERAL INFORMATION -->
                <game-details-relationships game="$ctrl.game" relationships="['platforms', 'genres', 'releaseYears']" bottom-attached="true"></game-details-relationships>
            </div>

            <!-- GAMEPLAY -->
            <h3 class="ui small header active title">
                <i class="dropdown icon"></i> {{'Gameplay'| translate}}
            </h3>
            <div class="content">
                <span class="small text" translate ng-if="data.isLoadingGame">{{'Loading'| translate}}...</span>
                <game-details-relationships game="$ctrl.game" relationships="['mechanics', 'gameModes', 'graphics', 'engines', 'durations']"></game-details-relationships>
            </div>

            <!-- PLOT -->
            <h3 class="ui small header active title">
                <i class="dropdown icon"></i> {{'Plot'| translate}}
            </h3>
            <div class="content">
                <span class="small text" translate ng-if="data.isLoadingGame">{{'Loading'| translate}}...</span>
                <game-details-relationships game="$ctrl.game" relationships="['series', 'atmospheres', 'themes', 'characters', 'creatures', 'locations', 'periods', 'weapons',  'vehicles', 'sportTeams', 'organizations', 'soundtracks']"></game-details-relationships>
            </div>

            <h3 class="ui small header active title">
                <i class="dropdown icon"></i> {{'Game Industry'| translate }}
            </h3>
            <div class="content">
                <span class="small text" translate ng-if="data.isLoadingGame">{{'Loading'| translate}}...</span>
                <game-details-relationships game="$ctrl.game" relationships="['developerCompanies', 'publisherCompanies', 'developers', 'awards', 'reviews', 'contentClassifications', 'contentCharacteristics']"></game-details-relationships>
            </div>
        </div>
    `,
    controller: "GameDetailsController",
    bindings: {
        index: "=",
        game: "=",
        gameLinks: "="
    }
});

