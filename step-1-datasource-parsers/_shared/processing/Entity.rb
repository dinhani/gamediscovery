class Entity
    attr_accessor :id, :id_specific, :name, :aliases, :type # identifiers
    attr_accessor :summary, :content, :word_count           # text
    attr_accessor :attributes, :categories                  # data

    def initialize(id = nil)
        @id = id
        @attributes = {}
        @word_count = {}
        @categories = []
    end
end
