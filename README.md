EmberJS Support for the Intellij Platform (WebStorm, PhpStorm, Rubymine, Intellij, etc)

## Installing the EmberJS plugin for Webstorm

Ideally we would like this plugin to be directly installable from Webstorm via the plugin repository.
However so far, this is a plugin under development and needs some testing before we release it
into the wild. So please help out.

It looks like you can manually install a plugin on Mac OSX like this:

- Create a jar file from this repo.
  http://stackoverflow.com/questions/19650711/how-to-create-a-java-jar-file-with-source-code-java-files
- Create a jar with IntelliJ
- http://blog.jetbrains.com/idea/2010/08/quickly-create-jar-artifact/

- Go to your *Applications* folder
- Select *View package content* from context menu (Command-click)
- Browse to `Content/plugins`
- Create a folder `EmberJS`
- in that folder create a `lib` folder
- add a `EmberJS.jar` to the `lib` folder

### Some time in the not too distant future...

As soon as we have confirmed that this first version of the plugin is functional ;)

- Navigate to File->Settings->Plugins
- Click "Browse Repositories"
- Select "EmberJS"
- Double-click (or right-click) and, when prompted, choose "Yes"
- Restart WebStorm
