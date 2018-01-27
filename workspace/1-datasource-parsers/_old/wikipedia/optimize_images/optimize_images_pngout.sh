@echo off
for %%a in ($GD_DATA_PARSED/wikipedia/images_converted/*.png) do (
    echo %%a
    pngout %%a
)
