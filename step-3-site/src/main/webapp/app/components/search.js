app.controller('SearchDesktopController', function ($scope, $rootScope, $location, $q, $translate, hotkeys,
        backendService, urlService, guiService, analyticsService) {

    // =========================================================================
    // DATA
    // =========================================================================
    // HTML ids
    var mainSearchId = "#search-main-search";
    var secondarySearchId = "#search-secondary-search";
    var gameResultsId = "#search-results";
    var gameDetailsId = "#search-details";
    var gameAccordionId = "#game-accordion";

    // ajax requests
    var previousFilterSearches = [];
    var previousGameSearches = [];

    // scope (search input)
    $scope.data = {};
    $scope.data.mainSearchText = "";
    $scope.data.mainSearchResults = [];
    $scope.data.secondarySearchText = "";
    $scope.data.secondarySearchResults = [];
    $scope.data.page = 1;

    // scope (filters)
    $scope.data.filters = [];
    $scope.data.categories = [];

    // scope (game results)    
    $scope.data.gameSearch = null;
    $scope.data.gameSearchRecommendedFilters = [];
    $scope.data.isSearchBySingleGame = false;

    // scope (selected game)
    $scope.data.selectedGameIndex = null;
    $scope.data.selectedGame = null;
    $scope.data.selectedGameLinks = [];
    $scope.data.selectedGameShowMore = true;

    // checks
    $scope.data.isSearchingGames = false;
    $scope.data.isLoadingGame = false;

    // =========================================================================
    // SHORTCUTS
    // =========================================================================
    // ESC (EXIT)
    hotkeys.bindTo($scope)
            // PREVIOUS GAME
            .add({
                combo: 'left',
                callback: function (e) {
                    if ($scope.data.selectedGame !== null) {
                        $scope.previousGame();
                    }
                }
            })
            // NEXT GAME
            .add({
                combo: 'right',
                callback: function (e) {
                    if ($scope.data.selectedGame !== null) {
                        $scope.nextGame();
                    }
                }
            });

    // =========================================================================
    // INITIALIZATION
    // =========================================================================
    $scope.initialize = function () {
        var requestParams = $location.search();

        // language        
        var language = requestParams['lang'];
        if (language !== undefined) {
            $rootScope.language(language);
        }

        // parse current filter parameters
        var filterUids = $location.path().split("/");
        filterUids = filterUids.slice(2, filterUids.length);

        // parse legacy filter parameters
        if (_.isEmpty(filterUids)) {
            filterUids = requestParams['q'];
        }

        // if does not have filters, empty filter array
        if (_.isEmpty(filterUids)) {
            filterUids = [];
        }
        // single filters are string, force array
        if (!_.isArray(filterUids)) {
            filterUids = [filterUids];
        }

        // load filters
        var start = performance.now();
        $scope
                // load filters
                .loadFilters(filterUids)
                // search games by filter
                .then(function () {
                    // update
                    updateGUI();

                    // search
                    $scope.searchGames();

                    // track speed                    
                    analyticsService.trackSpeed("Search", "Load filters", performance.now() - start);
                });
    };

    $scope.$on('onpopstate', function () {
        $scope.initialize();
    });

    backendService.getConcepts()
            .success(function (data) {
                $scope.data.categories = JSOG.decode(data);
            });

    // =========================================================================
    // FILTER
    // =========================================================================
    $scope.loadFilters = function (filterUids) {
        var promises = [];

        // load each concept entry individually
        $scope.data.filters = [];
        _.each(filterUids, function (filterUid) {
            var promise = backendService.getConceptEntry("", filterUid, false)
                    .success(function (c) {
                        $scope.data.filters.push(c);
                    })
                    .error(function () {
                        // remove filter if cannot load it?
                    });
            promises.push(promise);
        });

        return $q.all(promises);
    };

    $rootScope.addFilterFromExample = function (conceptEntry, clickEvent) {
        // prevent triggering link
        var shouldExit = processClickEvent(clickEvent);
        if (shouldExit) {
            return;
        }

        // process
        $scope.addFilter(conceptEntry);

        // track event
        analyticsService.trackEvent('Search', 'Add filter from example', conceptEntry.name);
    };
    $rootScope.addFilterFromSuggestion = function (conceptEntry) {
        // process
        $scope.data.filters = [];
        $scope.addFilter(conceptEntry);

        // track event
        analyticsService.trackEvent('Search', 'Add filter from suggestion', conceptEntry.name);
    };
    $rootScope.addFilterFromGame = function (conceptEntry) {
        // process
        $scope.addFilter(conceptEntry);

        // track event
        analyticsService.trackEvent('Search', 'Add filter from game', conceptEntry.name);
    };

    $scope.addFilter = function (conceptEntry) {
        // check if already exists, and if exists, do nothing
        var exists = _.any($scope.data.filters, function (f) {
            return f.uid === conceptEntry.uid;
        });
        if (exists) {
            return;
        }

        // de-select current game
        clearSelectedGame();
        guiService.hideModal(gameDetailsId);

        // reset pagination
        $scope.data.page = 1;

        // add filter
        $scope.data.filters.push(conceptEntry);

        // update
        updateGUI();

        // search games again
        $scope.searchGames();

        // animate
        guiService.scrollTop();

        // track event        
        analyticsService.trackEvent('Search', 'Add filter', conceptEntry.name);
    };

    $scope.removeFilter = function (conceptEntry, index) {
        // de-select current game
        clearSelectedGame();
        guiService.hideModal(gameDetailsId);

        // reset pagination
        $scope.data.page = 1;

        // remove filter
        $scope.data.filters.splice(index, 1);

        // update
        updateGUI();

        // search games again
        $scope.searchGames();

        // animate
        guiService.scrollTop();

        // track event        
        analyticsService.trackEvent('Search', 'Remove filter', conceptEntry.name);
    };  

    // =========================================================================
    // SEARCH
    // =========================================================================
    $scope.executeMainSearchCheckEnter = function (event) {
        if (event.which === 13) {
            $scope.executeMainSearch();
        }
    };
    $scope.executeMainSearch = function () {
        // cancel previous requests
        _.each(previousFilterSearches, function (previousSearch) {
            previousSearch.abort();
        });
        previousFilterSearches = [];

        // prepare search
        var request = backendService.getConceptEntries("", $scope.data.mainSearchText);
        previousFilterSearches.push(request);

        // track search
        analyticsService.trackSearch($scope.data.mainSearchText);

        // execute search
        var start = performance.now();
        request.success(function (data) {
            // prepare results
            $scope.data.mainSearchResults = JSOG.decode(data);

            // search in memory
            guiService.search(mainSearchId, $scope.data.mainSearchResults, mainSearchResultsOnSelectCallback);

            // track speed
            analyticsService.trackSpeed("Search", "Search filters", performance.now() - start);
        });
    };

    $scope.executeSecondarySearchCheckEnter = function (event) {
        if (event.which === 13) {
            $scope.executeSecondarySearch();
        }
    };
    $scope.executeSecondarySearch = function () {
        // cancel previous searches
        _.each(previousFilterSearches, function (previousSearch) {
            previousSearch.abort();
        });

        // prepare search        
        var request = backendService.getConceptEntries("", $scope.data.secondarySearchText);
        previousFilterSearches.push(request);

        // track search
        analyticsService.trackSearch($scope.data.secondarySearchText);

        // execute search
        var start = performance.now();
        request.success(function (data) {
            // prepare results
            $scope.data.secondarySearchResults = JSOG.decode(data);

            // search in memory
            guiService.search(secondarySearchId, $scope.data.secondarySearchResults, secondarySearchResultsOnSelectCallback);

            // track speed
            analyticsService.trackSpeed("Search", "Search filters", performance.now() - start);
        });
    };

    var mainSearchResultsOnSelectCallback = function (entry) {
        $scope.data.filters = [];
        secondarySearchResultsOnSelectCallback(entry);
    };
    var secondarySearchResultsOnSelectCallback = function (entry) {
        // add entry
        $scope.addFilter(entry);
        $scope.$apply();

        // clear input        
        $scope.data.mainSearchText = "";
        $scope.data.secondarySearchText = "";
        guiService.clear(mainSearchId + " input");
        guiService.clear(secondarySearchId + " input");

        // focus input
        //guiService.focus(secondarySearchId + " input");
    };

    $scope.loadMoreGames = function () {
        // increase page
        $scope.data.page++;

        // load additional games
        $scope.searchGames();
    };

    // =========================================================================
    // GAME
    // =========================================================================
    $scope.searchGames = function () {
        // do not search if empty
        if (_.isEmpty($scope.data.filters)) {
            return;
        }

        // cancel previous searches
        _.each(previousGameSearches, function (previousSearch) {
            previousSearch.abort();
        });
        previousGameSearches = [];

        // prepare search        
        $scope.data.isSearchingGames = true;
        if ($scope.data.page === 1) {
            $scope.data.gameSearch = null;
        }


        // do search
        var request = backendService.getRelatedGames($scope.data.filters, $scope.data.categories, $scope.data.page);
        previousGameSearches.push(request);

        var start = performance.now();
        request
                .success(function (data) {
                    // display games
                    var tempResults = JSOG.decode(data);
                    if ($scope.data.gameSearch === null) {
                        $scope.data.gameSearch = tempResults;
                    } else {
                        $scope.data.gameSearch.results = $scope.data.gameSearch.results.concat(tempResults.results);
                    }

                    // populate related fields
                    chooseRecommendedFilters($scope.data.gameSearch.results[0].game);

                    // track speed
                    analyticsService.trackSpeed("Search", "Search games", performance.now() - start);
                })
                .error(function () {
                })
                .finally(function () {
                    // remove searching
                    $scope.data.isSearchingGames = false;

                    // update
                    updateGUI();

                    // track page
                    analyticsService.trackPage();
                });
    };

    $scope.showGame = function (game, clickEvent) {
        // prevent triggering link
        var shouldExit = processClickEvent(clickEvent);
        if (shouldExit) {
            return;
        }

        // prepare load
        $scope.data.isLoadingGame = true;
        clearSelectedGame();

        // display game modal
        guiService.showModal(gameDetailsId,
                {
                    onHidden: function () {
                        $scope.closeGame();
                    }
                });

        // do load game information
        var start = performance.now();
        backendService.getConceptEntry("game", game)
                .success(function (data) {
                    // set data and update url
                    $scope.data.selectedGame = JSOG.decode(data);
                    $scope.data.selectedGameIndex = getSelectedGameIndex();
                    $scope.data.selectedGameShowMore = true;

                    // update
                    updateGUI();

                    // track speed
                    analyticsService.trackSpeed("Search", "Load game", performance.now() - start);
                })
                .finally(function () {
                    // disable loading
                    $scope.data.isLoadingGame = false;
                });

        // do load game images
        backendService.getGameLinks(game)
                .success(function (images) {
                    $scope.data.selectedGameLinks = images;
                })
                .error(function (error) {
                });

        // track page and event        
        analyticsService.trackEvent('Search', "Show game", game.name);
    };

    $scope.nextGame = function () {
        // track event
        analyticsService.trackEvent('Game', 'Next game', $scope.data.selectedGame.name);

        // load game
        $scope.showGame($scope.data.gameSearch.results[getSelectedGameNextIndex()].game);

        // animate
        guiService.scrollTop(gameAccordionId);
    };

    $scope.previousGame = function () {
        // track event
        analyticsService.trackEvent('Game', 'Previous game', $scope.data.selectedGame.name);

        // load game        
        $scope.showGame($scope.data.gameSearch.results[getSelectedGamePreviousIndex()].game);

        // animate
        guiService.scrollTop(gameAccordionId);
    };

    $scope.closeGame = function () {
        // track event before unsetting game to be able to track in Analytics
        analyticsService.trackEvent('Game', 'Close game', $scope.data.selectedGame.name);

        // remove selected game
        clearSelectedGame();

        // update
        updateGUI();
    };

    $scope.showMoreSummary = function () {
        jQuery("#game-summary").css("max-height", "");
        $scope.data.selectedGameShowMore = false;
    };

    function clearSelectedGame() {
        $scope.data.selectedGameIndex = null;
        $scope.data.selectedGame = null;
        $scope.data.selectedGameLinks = [];
    }

    function chooseRecommendedFilters(firstGame) {
        // alway reset
        $scope.data.gameSearchRecommendedFilters = [];

        // should not display recommended filters
        if (!$scope.data.isSearchBySingleGame) {
            return;
        }

        // choose recommended filters
        var recommendedFilters = [];
        _.each(firstGame.genres, function (genre) {
            recommendedFilters.push(genre);
        });
        _.each(firstGame.themes, function (theme) {
            recommendedFilters.push(theme);
        });
        $scope.data.gameSearchRecommendedFilters = recommendedFilters;
    }

    // =========================================================================
    // HELPER FUNCTIONS
    // =========================================================================
    // get the selected game index in the list of games that are being displayed
    function getSelectedGameIndex() {
        // null check
        if ($scope.data.selectedGame === null) {
            return 0;
        }

        // check
        return _.findIndex($scope.data.gameSearch.results, function (gameWithRelevance) {
            return $scope.data.selectedGame.uid === gameWithRelevance.game.uid;
        });
    }

    // get next game index
    function getSelectedGameNextIndex() {
        // index check
        var gameIndex = getSelectedGameIndex();
        gameIndex++;
        if (gameIndex >= $scope.data.gameSearch.results.length) {
            gameIndex = 0;
        }
        return gameIndex;
    }

    // get previous game index
    function getSelectedGamePreviousIndex() {
        // index check
        var gameIndex = getSelectedGameIndex();
        gameIndex--;
        if (gameIndex < 0) {
            gameIndex = $scope.data.gameSearch.results.length - 1;
        }
        return gameIndex;
    }

    // process a click event and return if the calling method should stop execution or processed
    function processClickEvent(clickEvent) {
        if (clickEvent !== undefined) {
            // left click with ctrl pressed or middle click
            if ((clickEvent.ctrlKey && clickEvent.which === 1) || clickEvent.which === 2) {
                return true;

            }
            // normal left click
            else {
                clickEvent.preventDefault(); // prevent triggering href
                return false;
            }
        }
    }

    // =========================================================================
    // DATE UPDATES
    // =========================================================================
    function updateGUI() {
        // search type
        updadteSearchType();

        // descriptions
        updateHeaderAndTitle();
        updateURL();

        // components
        guiService.dropdown();
        guiService.accordion();
        //guiService.cards();
    }
    $rootScope.updateGUI = updateGUI;

    function updadteSearchType() {
        // not by single game
        if ($scope.data.filters.length === 0) {
            $scope.data.isSearchBySingleGame = false;
            return;
        }

        // check if by single game
        var onlyOneGame = _.filter($scope.data.filters, function (filter) {
            return s.startsWith(filter.uid, "game-");
        }).length === 1;
        $scope.data.isSearchBySingleGame = onlyOneGame;
    }

    function updateHeaderAndTitle() {
        // no search
        if ($scope.data.filters.length === 0) {
            // header
            $rootScope.data.header = "Game Discovery";

            // title
            $translate('PAGE.HEADER.EMPTY')
                    .then(function (translation) {
                        $rootScope.data.title = "Game Discovery | " + translation;
                    });

            // description
            $translate('PAGE.DESCRIPTION.EMPTY')
                    .then(function (translation) {
                        $rootScope.data.description = translation;
                    });

            return;
        }

        // checks
        var firstFilter = $scope.data.filters[0];
        var isSingleFilter = $scope.data.filters.length === 1;
        var isCreature = s.startsWith(firstFilter.uid, "creature-");
        var isGame = s.startsWith(firstFilter.uid, "game-");
        var isLocation = s.startsWith(firstFilter.uid, "location-");
        var isPlatform = s.startsWith(firstFilter.uid, "platform-");
        var isPeriod = s.startsWith(firstFilter.uid, "period-");

        // single creature
        if (isSingleFilter && isCreature) {
            doUpdateHeaderAndTitleAndDescription('PAGE.HEADER.CREATURE', firstFilter.name);
            return;
        }

        // single game
        if (isSingleFilter && isGame) {
            doUpdateHeaderAndTitleAndDescription('PAGE.HEADER.GAME', firstFilter.name);
            return;
        }

        // single location
        if (isSingleFilter && isLocation) {
            doUpdateHeaderAndTitleAndDescription('PAGE.HEADER.LOCATION', firstFilter.name);
            return;
        }

        // single platform
        if (isSingleFilter && isPlatform) {
            doUpdateHeaderAndTitleAndDescription('PAGE.HEADER.PLATFORM', firstFilter.name);
            return;
        }

        // single period
        if (isSingleFilter && isPeriod) {
            doUpdateHeaderAndTitleAndDescription('PAGE.HEADER.PERIOD', firstFilter.name);
            return;
        }

        // everything else
        var searches = _.map($scope.data.filters, function (filter) {
            return filter.name;
        }).join(" / ");
        doUpdateHeaderAndTitleAndDescription('PAGE.HEADER.ANY', searches);
    }

    function doUpdateHeaderAndTitleAndDescription(translationKey, name) {
        $translate(translationKey, {name: name}).then(function (headerTranslation) {
            $rootScope.data.header = headerTranslation;
            $rootScope.data.title = headerTranslation + " | Game Discovery";
            $translate('PAGE.DESCRIPTION.ANY', {name: headerTranslation}).then(function (descriptionTranslation) {
                $rootScope.data.description = descriptionTranslation;
            });
        });
    }

    // =========================================================================
    // META INFO (URL)
    // =========================================================================
    function updateURL() {
        $location.url(urlService.recommendations($scope.data.filters));

        // if called by jQuery, force apply
        if (!$scope.$$phase) {
            $scope.$apply();
        }
    }
});
