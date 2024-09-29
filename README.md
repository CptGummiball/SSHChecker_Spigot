# SSHChecker Spigot

A simple Minecraft (Spigot) plugin that periodically checks the availability of an SSH server and optionally sends error messages to a Discord webhook when the server is unreachable.

## Features

- **SSH Server Monitoring**: Automatically checks if an SSH server is reachable.
- **Discord Webhook Integration**: Optionally sends error messages to a Discord channel via a webhook.
- **Customizable Check Interval**: The interval between SSH checks can be set in the configuration file.
- **Configurable SSH Parameters**: You can specify the SSH server host, port, user, password, and timeout.

## Installation

1. **Download the plugin**: Clone this repository or download the latest release.
2. **Place the plugin**: Move the `.jar` file to the `plugins` directory of your Spigot server.
3. **Start your server**: Run the Minecraft server to generate the default configuration file.
4. **Configure the plugin**: Edit the `config.yml` file in the `plugins/SSHChecker` directory with your SSH server details and Discord webhook settings.
5. **Restart your Server**

## Configuration

The `config.yml` file allows you to customize the SSH server details, Discord webhook, and the interval between SSH checks.

### Example `config.yml`

```yaml
ssh:
  host: "yourSSHhost"         # The hostname or IP address of your SSH server
  port: 22                    # The port of your SSH server (default 22)
  user: "yourSSHuser"         # The SSH username
  password: "yourSSHpassword" # The SSH password
  timeout: 5000               # SSH connection timeout in milliseconds
  interval: 72000             # Check interval in ticks (72000 ticks = 60 minutes)

discord:
  use_webhook: false          # Enable/disable Discord webhook notifications
  webhook_url: "yourWebhookUrl" # Discord webhook URL to send error messages to
````

### Configuration Parameters
- ``ssh.host``: The hostname or IP address of the SSH server.
- ``ssh.port``: The SSH port (default: 22).
- ``ssh.user``: The SSH username.
- ``ssh.password``: The SSH password (store securely!).
- ``ssh.timeout``: The timeout for the SSH connection in milliseconds.
- ``ssh.interval``: The interval for checking the SSH connection in Minecraft ticks (20 ticks = 1 second).
- ``discord.use_webhook``: Whether to use Discord webhook notifications (true/false).
- ``discord.webhook_url``: The URL for your Discord webhook.

## Commands and Permissions
This plugin does not have any commands or permissions.

## Usage
Configure your SSH server and Discord webhook settings in the config.yml.
The plugin will automatically check the SSH server at the interval you defined.
If the SSH server is unreachable and the Discord webhook is enabled, a message will be sent to the specified Discord channel.

## Example Use Case
- **Server Monitoring:** Use this plugin to ensure your SSH server (for example, a VPS or dedicated server) is online and reachable from your Minecraft server. If the server goes down, you will receive immediate notifications on your Discord channel.

## Building From Source
1. Clone the repository:
```bash
git clone https://github.com/yourusername/SSHChecker.git
````
2. Open the project in your preferred Java IDE.
3. Build the project using Maven or your build tool of choice.

## Dependencies
Java 17 or Later  
[Spigot API 1.20](https://helpch.at/docs/1.20/)  
[JSch (Java Secure Channel)](https://github.com/mwiede/jsch)  

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributions
Feel free to fork this repository and submit pull requests if you'd like to contribute!

## Contact
For questions or suggestions, feel free to open an issue.
