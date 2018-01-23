Vagrant.configure("2") do |config|
    # ==========================================================================
    # MACHINE
    # ==========================================================================
    config.vm.box = "generic/ubuntu1710"

    config.vm.provider "virtualbox" do |v|
        v.gui = false
        v.memory = 2048
    end

    # ==========================================================================
    # FOLDERS
    # ==========================================================================
    config.vm.synced_folder "workspace", "/home/vagrant/workspace", create: true

    # ==========================================================================
    # HARDWARE
    # ==========================================================================
    config.vm.provider "virtualbox" do |v|
        v.gui = false
        v.memory = 2048
    end

    # ============================================================================
    # PROVISIONING
    # ============================================================================
    config.vm.provision "shell", path: "provisioning.sh", privileged: false
end
