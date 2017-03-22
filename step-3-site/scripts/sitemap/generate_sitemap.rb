require 'neography'
require 'jrjackson'
require 'sitemap_generator'

# query all uids
puts "Connecting"
neo = Neography::Rest.new({:authentication => 'basic', :username => ENV['GD_NEO4J_CONN_USER'], :password => ENV['GD_NEO4J_CONN_PASSWORD']})

puts "Querying concept entries"
response = neo.execute_query("MATCH (n:ConceptEntry) RETURN n.uid ORDER BY n.uid")
puts "#{response['data'].size} concept entries found"

# generate sitemap
puts "Generating sitemap"
SitemapGenerator::Sitemap.public_path = './../../src/main/webapp/app/files'
SitemapGenerator::Sitemap.default_host = 'http://www.gamediscovery.net'
SitemapGenerator::Sitemap.create do

  # root
  add '/recommendations', :changefreq => 'weekly'

  # iterate results
  response['data'].each do |row|
    uid = row[0]

    # recommendations
    ['en', 'es', 'fr', 'it', 'pt'].each do |lang|
      add "recommendations/#{uid}?lang=#{lang}", :changefreq => 'weekly'
    end
  end
end
