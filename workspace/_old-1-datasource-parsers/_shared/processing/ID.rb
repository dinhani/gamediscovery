class ID

  def self.parse(name)
    name
      .strip
      .downcase
      .tr("!?.:/\\'", "")
      .tr(",_ '", "-")
      .gsub(/-+/, "-")
  end

end
