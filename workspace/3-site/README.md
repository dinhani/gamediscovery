# step-3-site

## Running in development

Just execute:

```
mvn spring-boot:run
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