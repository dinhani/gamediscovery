// =============================================================================
// HTML
// =============================================================================
app.component("gameCard", {
    templateUrl: 'game-card.html',
    bindings: {
        index: "@",
        game: "=",
        showGame: "&"
    }
});