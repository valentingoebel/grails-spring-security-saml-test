It would seem that the [Grails Spring Security SAML
Plugin](https://github.com/jeffwils/grails-spring-security-saml) does not work with the current
version of Grails - 3.3.3. So I created this project based on the [jeffwills
saml-plugin-test](https://github.com/jeffwils/saml-plugin-test) in so much of what config to use,
and items in `conf/security` -  as I did successfully use these with Grails 3.1.9.

Of note:

* I had to use Spring Security Core Plugin 3.2.1 - as I think the older 3.1.2 does not work with
  3.3.x; and
* Although not in the SAML plugin instructions, I've found that I need to modify init/Application.groovy inline
  with what is done in `saml-plugin-test` - i.e. Add `@EnableAutoConfiguration(exclude =
  [SecurityFilterAutoConfiguration])`
* There is no substance to this project, just the bare bones and domain classes for Spring Security.

Steps taken to create:

1. grails create-app samltest
2. Modified build.gradle to add additional repositories and the three additional dependencies
3. ran `./grailsw s2quickstart samltest UserAcct Role`
4. Copied across `conf/security` from other working 3.1.9 project
5. Copied across required config into `conf/application.yml`
6. removed `conf/application.groovy`
7. Modified `init/Application.groovy`

So with all done (as previously done for 3.1.9), things are left broken with the following occurring:

First I execute:

    ./grailsw run-app

And then observe:

<pre>
| Running application...

Configuring Spring Security Core ...
... finished configuring Spring Security Core

Active Flag true
Configuring Spring Security SAML ...
Importing beans from classpath:security/springSecuritySamlBeans.xml...
Registering metadata key: ping and value: security/idp-local.xml
Sp File exists security/sp.xml
...finished configuring Spring Security SAML
2018-03-08 15:20:09.721 ERROR --- [           main] o.s.boot.SpringApplication               : Application startup failed

org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'methodValidationPostProcessor' defined in class path resource [org/springframework/boot/autoconfigure/validation/ValidationAutoConfiguration.class]: Unsatisfied dependency expressed through method 'methodValidationPostProcessor' parameter 0; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'hibernateDatastoreServiceRegistry': Cannot resolve reference to bean 'hibernateDatastore' while setting constructor argument; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'hibernateDatastore': Bean instantiation via constructor failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.grails.orm.hibernate.HibernateDatastore]: Constructor threw exception; nested exception is java.lang.NullPointerException
</pre>

Followed by much more, but you get the idea. Hibernate/GORM is unhappy.
