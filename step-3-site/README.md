# step-3-site

## Running in development

Install Bower dependencies with:

```
bower install
```

Generate Lucene index files with:

```
scripts/lucene/generate_lucene.bat
```

Build and run with:
```
build.bat
local-start.bat
```


## Running in production


Set the environment variable:

```
GD_ENVIRONMENT=production
```

Generate the sitemap files with:

```
scripts/lucene/generate_lucene.bat
```

Generate the minified JS and CSS with:

```
mvn wro4j:run
```
