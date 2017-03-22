# parse input
exit if ARGV.empty?
url_to_render = ARGV[0]

# prepare driver
require "selenium-webdriver"
caps = Selenium::WebDriver::Remote::Capabilities.phantomjs
caps["phantomjs.page.settings.loadImages"] = false
driver = Selenium::WebDriver.for :phantomjs, :desired_capabilities => caps

# dimension
dimension = Selenium::WebDriver::Dimension.new
dimension.width = 1920
dimension.height = 1080
driver.manage.window.size = dimension

# render page
driver.get(url_to_render)

# wait
wait = Selenium::WebDriver::Wait.new({:timeout => 20})
wait.until { driver.find_element(:class => 'prerender-wait') }

# output render
#driver.save_screenshot("page.png")
puts driver.page_source

# shutdown
driver.quit