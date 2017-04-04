app.service('guiService', function ($rootScope, $timeout) {

    // =========================================================================
    // COMPONENTS (ACCORDION)
    // =========================================================================
    this.accordion = function () {
        $timeout(function () {
            jQuery('.ui.accordion').accordion({
                exclusive: false
            });

            // open first
            var tabsToOpen = jQuery('.ui.accordion .content').length;
            for (var i = 0; i < tabsToOpen; i++) {
                jQuery('.ui.accordion').accordion('open', i);
            }
        }, 100);
    };

    // =========================================================================
    // COMPONENTS (CARD)
    // =========================================================================
    this.cards = function () {
        $timeout(function () {
            jQuery('.concept-entry-image').dimmer({ on: 'hover' });
        }, 100);
    };

    // =========================================================================
    // COMPONENTS (DIMMER)
    // =========================================================================
    this.showDimmer = function (containerSelector) {
        if (_.isUndefined(containerSelector)) {
            containerSelector = ".ui.dimmer";
        }
        $timeout(function () {
            jQuery(containerSelector).dimmer('show');
        }, 100);
    };

    this.hideDimmer = function (containerSelector) {
        if (_.isUndefined(containerSelector)) {
            containerSelector = ".ui.dimmer";
        }
        $timeout(function () {
            jQuery(containerSelector).dimmer('hide');
        }, 200);
    };

    // =========================================================================
    // COMPONENTS (DROPDOWN)
    // =========================================================================
    this.dropdown = function () {
        $timeout(function () {
            jQuery(".dropdown").dropdown();
        }, 100);
    };

    // =========================================================================
    // COMPONENTS (INPUT)
    // =========================================================================
    this.focus = function (inputSelector) {
        $timeout(function () {
            jQuery(inputSelector).focus();
        }, 100);
    };
    this.clear = function (inputSelector) {
        $timeout(function () {
            jQuery(inputSelector).val("");
        }, 100);
    };

    // =========================================================================
    // COMPONENTS (MODAL)
    // =========================================================================
    this.showModal = function (modalSelector, additionalOptions) {
        // validate additional options
        if (!_.isObject(additionalOptions)) {
            additionalOptions = {};
        }
        //additionalOptions.transition = 'fly up';
        additionalOptions.duration = 300;
        additionalOptions.observeChanges = true;

        // show modal
        jQuery(modalSelector)
            .modal(additionalOptions)
            .modal('show');
    };

    this.hideModal = function (modalSelector) {
        jQuery(modalSelector).modal('hide');
    };
    this.closeModal = this.hideModal;

    // =========================================================================
    // COMPONENTS (POPUP)
    // =========================================================================
    this.popup = function () {
        $timeout(function () {
            jQuery("[data-html]").popup();
        }, 100);
    }

    this.showPopup = function (linkSelector, popupSelector) {
        // initialize if necessary
        if (!jQuery(linkSelector).popup('exists')) {
            jQuery(linkSelector).popup({
                on: 'click',
                popup: jQuery(popupSelector),
                position: 'bottom left',
                exclusive: true,
                closable: false,
                setFluidWidth: false
            });
        }

        // show
        jQuery(linkSelector).popup('show');
    };

    this.hidePopup = function (selector) {
        jQuery(selector).popup('hide');
    };
    this.closePopup = this.hidePopup;

    // =========================================================================
    // COMPONENTS (PAGE)
    // =========================================================================
    this.title = function (title) {
        $rootScope.data.page.title = title;
    };

    this.description = function (description) {
        $rootScope.data.page.description = description;
    };

    // =========================================================================
    // COMPONENTS (SEARCH)
    // =========================================================================
    this.search = function (searchSelector, datasource, onSelect) {
        // get UI element
        var searchInput = jQuery(searchSelector);
        //searchInput.search("destroy");

        // configure datasource
        searchInput.search.settings.source = datasource;
        searchInput.search.settings.maxResults = 20;

        // configure search fields
        searchInput.search.settings.searchFields = ["uid", "name"];
        searchInput.search.settings.fields.title = "displayName";
        //searchInput.search.settings.fields.image = "imageFullPath";
        searchInput.search.settings.fields.description = "typeName";


        // behaviors
        searchInput.search.settings.selectFirstResult = true;
        searchInput.search.settings.onSelect = onSelect;

        // onResultsClose call
        searchInput.search.settings.onResultsClose = function () {
            searchInput.search("destroy");
        };

        // enrich datasource
        _.each(datasource, function (element) {
            // type
            element.typeName = element.type.name;

            // alias
            element.displayName = element.name;
            if (!_.isEmpty(element.alias)) {
                element.displayName += " <span class='alias'> (" + element.alias + ")</span>";
            }

            // platforms
            if (element.type.uid === 'Game') {
                element.typeName += " (" + _.map(element.platforms, function (platform) {
                    return platform.name;
                }).join(", ") + ")";
            }
        });

        // do search
        var resultsHTML = searchInput.search("generate results", { results: datasource });
        searchInput.search("add results", resultsHTML);
        searchInput.search("inject id", datasource);
    };

    this.hideSearch = function (searchSelector) {
        jQuery(searchSelector).search("hide results");
    }
    this.closeSearch = this.hideSeaarch;

    // =========================================================================
    // COMPONENTS (TABS)
    // =========================================================================
    this.tabs = function () {
        $timeout(function () {
            jQuery('.ui.menu .item').tab();
        }, 100);
    };

    // =========================================================================
    // WINDOW
    // =========================================================================
    this.scrollTop = function (elementToScrollSelector) {
        if (_.isUndefined(elementToScrollSelector)) {
            elementToScrollSelector = 'body';
        };
        jQuery(elementToScrollSelector).animate({ scrollTop: 0 }, 250);
    };

    this.heightToFooter = function (elementToChangeHeigthSelector, marginToSubtract) {
        var elementToChangeHeight = document.getElementById(elementToChangeHeigthSelector);

        // calculate height (I copied from Sprint code, I don't know exactly why it works, but it does)
        var elementTopPosition = jQuery(elementToChangeHeight).position().top;
        var newHeight = $rootScope.data.window.height - elementTopPosition - 10;

        if (marginToSubtract !== undefined) {
            newHeight = newHeight - marginToSubtract;
        }

        // set height
        return newHeight + "px";
    };
});
