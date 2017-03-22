require 'sinatra'
require 'sinatra/reloader'

require_relative 'domain/Wikidata.rb'
require_relative 'domain/Item.rb'

# ==============================================================================
# ROUTES (QUERY)
# ==============================================================================
get '/' do
  erb :index
end

get '/no-developer' do
  execute("P178")
end

get '/no-genre' do
  execute("P136")
end

get '/no-platform' do
  execute("P400")
end

get '/no-publisher' do
  execute("P123")
end

get '/no-series' do
  execute("P179")
end

# ==============================================================================
# CREATING
# ==============================================================================
get '/add-property' do
  "Hello #{params['prop']} = #{params['value']}"
end


# ==============================================================================
# INTERNAL
# ==============================================================================
def execute(property, options = {})
  # query
  wikidata = Wikidata.new
  items = wikidata::query_missing(property)

  # render
  erb :listing, :locals => {:items => items, :property => property, :options => options}
end
