app.directive('fillHeight', function ($window) {
    return {
        restrict: 'A',
        link: function (scope, element, attributes) {
            // =================================================================
            // INIT
            // =================================================================
            angular.element($window).on('resize', _.debounce(onWindowResize, 250))
            onWindowResize()

            // =================================================================
            // RESIZE
            // =================================================================
            function onWindowResize() {
                // window
                let windowHeight = $window.innerHeight

                // element
                let elementOffsetTop = 0
                var elementToResize
                if (attributes.fillHeight) {
                    elementToResize = jQuery(element).find(attributes.fillHeight)
                } else {
                    elementToResize = jQuery(element[0])
                }
                if (elementToResize.length) {
                    elementOffsetTop = elementToResize.offset().top
                }

                // footer
                let footerHeight = 0
                if (attributes.fillHeightFooter) {
                    let footerElement = jQuery(attributes.fillHeightFooter)
                    if (footerElement.length) {
                        footerHeight = jQuery(footerElement).outerHeight()
                    }
                }

                // set height
                if (elementToResize.length) {
                    let bottomPadding = 4;
                    let elementHeight = windowHeight - elementOffsetTop - footerHeight - bottomPadding
                    elementToResize.css('min-height', elementHeight + 'px')
                }
            }
        }
    }
})
