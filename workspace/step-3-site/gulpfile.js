const browserSync = require('browser-sync').create();
const filesExist = require('files-exist');
const gulp = require('gulp')
const $ = require('gulp-load-plugins')()

// =============================================================================
// CSS
// =============================================================================
gulp.task('css', ['css-fonts'], function(){
    // LIBS
    let libs = gulp.src(filesExist([
        'node_modules/semantic-ui/dist/semantic.min.css',
        'node_modules/ng-tags-input/build/ng-tags-input.min.css'
    ]))
    .pipe($.cssRemoveAttributes(['color'], {compress: true}))
    .pipe($.concat('gamediscovery-libs.css'))

    // APP
    let appSass = gulp.src(
        [
            'src/main/resources/static/app/styles/base/*.scss',
            'src/main/resources/static/app/styles/layout/*.scss',
            'src/main/resources/static/app/styles/components/*.scss'
        ])
        .pipe($.concat('gamediscovery-sass.scss'))
        .pipe($.sass().on('error', $.sass.logError))
        .pipe($.concat('gamediscovery-sass.css'))

    // MERGE
    return $.merge(libs, appSass)
        // minify
        .pipe($.concat('gamediscovery.css'))
        .pipe($.cleanCss())
        .pipe(gulp.dest('src/main/resources/static/dist'))
        .pipe(gulp.dest('target/classes/static/dist'))
        // gzip
        .pipe($.gzip({ gzipOptions: { level: 9 } }))
        .pipe(gulp.dest('src/main/resources/static/dist'))
})

gulp.task('css-fonts', function(){
    return gulp.src('node_modules/semantic-ui/dist/themes/**')
        .pipe(gulp.dest('src/main/resources/static/dist/themes'))
})

// =============================================================================
// JAVASCRIPT
// =============================================================================
gulp.task('js', function(){
    // LIBS
    let libs = gulp.src(filesExist([
        // general
        'node_modules/underscore/underscore-min.js',
        'node_modules/underscore.string/dist/underscore.string.min.js',
        'node_modules/jsog/lib/JSOG.js',
        'node_modules/jquery/dist/jquery.min.js',
        'node_modules/semantic-ui/dist/semantic.min.js',
        // angular
        'node_modules/angular/angular.js',
        'node_modules/angular-img-fallback/angular.dcb-img-fallback.min.js',
        'node_modules/ng-tags-input/build/ng-tags-input.min.js',
        'node_modules/angular-hotkeys/build/hotkeys.min.js'
    ]))
    .pipe($.concat('gamediscovery-libs.js'))

    // APP
    let app = gulp.src('src/main/resources/static/app/**/*.js')
        .pipe($.angularEmbedTemplates())
        .pipe($.babel({presets: ['es2015']}))
        .pipe($.uglify({mangle:false}))
        .pipe($.concat('gamediscovery-app.js'))

    // MERGE
    return $.merge(libs, app)
        // minify
        .pipe($.concat('gamediscovery.js'))
        .pipe(gulp.dest('src/main/resources/static/dist'))
        .pipe(gulp.dest('target/classes/static/dist'))
        // gzip
        .pipe($.gzip({ gzipOptions: { level: 9 } }))
        .pipe(gulp.dest('src/main/resources/static/dist'))
})

// =============================================================================
// HTML
// =============================================================================
gulp.task('html', function(){
    gulp.src('src/main/resources/static/index.html')
        .pipe($.htmlmin({collapseWhitespace: true, collapseBooleanAttributes:true}))
        .pipe(gulp.dest('src/main/resources/static/dist'))
        .pipe(gulp.dest('target/classes/static/dist'))
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
gulp.task('browser-sync', function() {
    browserSync.init({proxy: "http://localhost:9000"});
});
gulp.task('watch', ['browser-sync', 'build'], function(){
    gulp.watch('src/main/resources/static/app/styles/*.css',     ['build-reload'])
    gulp.watch('src/main/resources/static/app/styles/**/*.scss', ['build-reload'])
    gulp.watch('src/main/resources/static/app/**/*.js',          ['build-reload'])
    gulp.watch('src/main/resources/static/app/**/*.html',        ['build-reload'])
    gulp.watch('src/main/resources/static/index.html',           ['build-reload'])
})

gulp.task('build-reload', ['build'], function () {
    return browserSync.reload()
})
gulp.task('build', ['js', 'css', 'html'])
gulp.task('default', ['build'])