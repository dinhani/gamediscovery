app.service('uidService', function () {    

    var spaceCompactationRegex = new RegExp(/\s+/g);
    var spaceRegex = new RegExp(/\s/g);
    var symbolRegex = new RegExp(/[^\w\s-]/gi);

    this.generateUid = function (name) {
        if(_.isEmpty(name)){
            return "";
        }
        
        // lowercase
        var id = name.toLowerCase();
        
        // trim
        id = id.trim();
        
        // compact spaces
        id = id.replace(spaceCompactationRegex, ' ');
        
        // transform spaces into dashes
        id = id.replace(spaceRegex, '-');
        
        // remove special symbols
        id = id.replace(symbolRegex, '');        
        id = id.replace('_', '');
        
        return id;
    };
});