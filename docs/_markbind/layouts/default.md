<head-bottom>
  <link rel="stylesheet" href="{{baseUrl}}/stylesheets/main.css">
</head-bottom>

<header sticky>
  <navbar type="dark">
    <a slot="brand" href="{{baseUrl}}/index.html" title="Home" class="navbar-brand">SpyGlass</a>
    <li><a href="{{baseUrl}}/index.html" class="nav-link">Home</a></li>
    <li><a href="{{baseUrl}}/UserGuide.html" class="nav-link">User Guide</a></li>
    <li><a href="{{baseUrl}}/DeveloperGuide.html" class="nav-link">Developer Guide</a></li>
    <li><a href="{{baseUrl}}/AboutUs.html" class="nav-link">About Us</a></li>
    <li><a href="https://github.com/se-edu/addressbook-level3" target="_blank" class="nav-link"><md>:fab-github:</md></a>
    </li>
    <li slot="right">
      <form class="navbar-form">
        <searchbar :data="searchData" placeholder="Search" :on-hit="searchCallback" menu-align-right></searchbar>
      </form>
    </li>
  </navbar>
</header>

<div id="flex-body">
  <nav id="site-nav">
    <div class="site-nav-top">
      <div class="fw-bold mb-2" style="font-size: 1.25rem;">Site Map</div>
    </div>
    <div class="nav-component slim-scroll">
      <site-nav>
* [Home]({{ baseUrl }}/index.html)
* [User Guide]({{ baseUrl }}/UserGuide.html) :expanded:
  * [Getting Started]({{ baseUrl }}/UserGuide.html#getting-started)
  * [User Interface Overview]({{ baseUrl }}/UserGuide.html#user-interface-overview)
  * [App Modes]({{ baseUrl }}/UserGuide.html#app-modes-locked-and-unlocked)
  * [Features]({{ baseUrl }}/UserGuide.html#features)
    * [Command Summary]({{ baseUrl }}/UserGuide.html#command-summary)
    * [Unrestricted Commands]({{ baseUrl }}/UserGuide.html#unrestricted-commands)
    * [Restricted Commands]({{ baseUrl }}/UserGuide.html#restricted-commands)
  * [FAQ]({{ baseUrl }}/UserGuide.html#faq)
  * [Glossary]({{ baseUrl }}/UserGuide.html#glossary)
* [Developer Guide]({{ baseUrl }}/DeveloperGuide.html) :expanded:
  * [Acknowledgements]({{ baseUrl }}/DeveloperGuide.html#acknowledgements)
  * [Setting Up]({{ baseUrl }}/DeveloperGuide.html#setting-up-getting-started)
  * [Design]({{ baseUrl }}/DeveloperGuide.html#design)
    * [Architecture]({{ baseUrl }}/DeveloperGuide.html#architecture)
    * [UI component]({{ baseUrl }}/DeveloperGuide.html#ui-component)
    * [Security component]({{ baseUrl }}/DeveloperGuide.html#security-component)
    * [Logic component]({{ baseUrl }}/DeveloperGuide.html#logic-component)
    * [Model component]({{ baseUrl }}/DeveloperGuide.html#model-component)
    * [Storage component]({{ baseUrl }}/DeveloperGuide.html#storage-component)
  * [Implementation]({{ baseUrl }}/DeveloperGuide.html#implementation)
    * [Setup Password]({{ baseUrl }}/DeveloperGuide.html#setup-password)
    * [Mode Switching]({{ baseUrl }}/DeveloperGuide.html#lock-unlock-mode-switching)
  * [Dev-Ops]({{ baseUrl }}/DeveloperGuide.html#documentation-logging-testing-configuration-dev-ops)
  * [Appendix: Requirements]({{ baseUrl }}/DeveloperGuide.html#appendix-requirements)
  * [Appendix: Testing]({{ baseUrl }}/DeveloperGuide.html#appendix-instructions-for-manual-testing)
* [About Us]({{ baseUrl }}/AboutUs.html)
      </site-nav>
    </div>
  </nav>
  <div id="content-wrapper">
    {{ content }}
  </div>
  <nav id="page-nav">
    <div class="nav-component slim-scroll">
      <page-nav />
    </div>
  </nav>
  <scroll-top-button></scroll-top-button>
</div>

<footer>
  <div class="text-center">
    <small>[<md>**Powered by**</md> <img src="https://markbind.org/favicon.ico" width="30"> {{MarkBind}}, generated on {{timestamp}}]</small>
  </div>
</footer>
