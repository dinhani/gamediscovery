const filesExist = require('files-exist');
const gulp = require('gulp')
const $ = require('gulp-load-plugins')()

// =============================================================================
// CSS
// =============================================================================
gulp.task('css', function(){
    // LIBS
    let libs = gulp.src(filesExist([
        'node_modules/semantic-ui/dist/semantic.min.css',
        'node_modules/ng-tags-input/build/ng-tags-input.min.css'
    ]))
    .pipe($.concat('gamediscovery-libs.css'))

    // APP
    let app = gulp.src('src/main/resources/static/**/*.css')
        .pipe($.concat('gamediscovery.css'))

    // MERGE
    return $.merge(libs,app)
        .pipe($.concat('gamediscovery.css'))
        .pipe(gulp.dest('src/main/resources/static/dist'))
})

// =============================================================================
// JAVASCRIPT
// =============================================================================
gulp.task('js', function(){
    // LIBS
    let libs = gulp.src(filesExist([
        // general
        'node_modules/underscore/underscore-min.min.js',
        'node_modules/underscore.string/dist/underscore.string.min.js',
        'node_modules/jsog/lib/JSOG.js',
        'node_modules/jquery/dist/jquery.min.js',
        'node_modules/semantic-ui/dist/semantic.min.js',
        // angular
        'node_modules/angular/angular.min.js',
        'node_modules/angular-cookies/angular-cookies.min.js',
        'node_modules/angular-translate/dist/angular-translate.min.js',
        'node_modules/angular-translate-storage-cookie/angular-translate-storage-cookie.min.js',
        'node_modules/angular-img-fallback/angular.dcb-img-fallback.min.js',
        'node_modules/ng-tags-input/build/ng-tags-input.min.js',
        'node_modules/angular-hotkeys/build/hotkeys.min.js'
    ]))
    .pipe($.concat('gamediscovery-libs.js'))

    // APP
    let app = gulp.src('src/main/resources/static/app/**/*.js')
        .pipe($.concat('gamediscovery-app.js'))

    // MERGE
    return $.merge(libs, app)
        .pipe($.concat('gamediscovery.js'))
        .pipe(gulp.dest('src/main/resources/static/dist'))
})

// =============================================================================
// HTML
// =============================================================================
gulp.task('html', function(){
    gulp.src('src/main/resources/static/**/*.html')
        .pipe($.htmlmin({collapseWhitespace: true}))
        .pipe(gulp.dest('src/main/resources/static/dist'))
})

// =============================================================================
// CLEAN
// =============================================================================
gulp.task('clean', function(){
    return gulp.src('src/main/resources/static/dist')
        .pipe($.clean())
})

// =============================================================================
// EXECUTION
// =============================================================================
gulp.task('default', ['css', 'js', 'html'])