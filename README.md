# LinkPreview for Confluence
![](src/main/resources/images/pluginLogo.png)

Preview the content of images, documents and hyperlinks of your Confluence pages.

## Documentation

This plugin changes the way you see the links in Confluence. With Link Preview for Confluence, you do not need to open links to see their content, you just need to pass the mouse over and a popup is shown with the link content. This plugin currently previews images, pdf files and hyperlinks.

In Confluence's Administration you can define the Link Preview behavior, such as:

* Define the file types that you wish to preview
* Define the hierarchy of link elements to preview
* Define the popup layout using CSS
* Define if the popup shows or not the file name
* Define if the popup has the same header color as defined in Confluence theme.

### App Installation
To get it working you need to do the below steps:

* Log into your Confluence instance as an admin.
* Click Manage apps from the left-hand side of the page.
* Locate the Upload app option, select the artifact and click on upload.
* You're all set

### App Administration
There are some configuration that can be made in order to define the desired add-on behaviour. Those configurations are described below.

| Confiruation | Description |
| ------------- | ------------- |
| *CSS Classes to Preview Content*| Defines the CSS classes that will be available to preview.|
|*Preview Image Types* | List of Image content types that will be possible to preview.|
| *Preview PDF*|	Set if it's possible to preview PDF documents. |
|*Preview Other Types* | Content Types of other resource type that can be previewed.|
|*Preview CSS* |Defines the preview popup theme. |
|*Preview WebPages* |Set if it's possible to preview Web pages. |
|*Use Space Header Color* | Defines if the popup color should be the same of the current space.|
| *Show Resource Name*|Set if the name should or not be shown in the preview popup. |
| *Fixed Image Preview*| Set if the popup should be fixed or not.|
| *Click To Preview Image* | Define the preview should be made on the fly or should be shown a button before preview. If the button is shown, a click is needed to preview. | 



## How to build the app?

### Requirements
* Java 8
* Atlassian SDK

### Building
```shell script
atlas-clean package
```

## Suggestions
Pull Requests are most welcome.