package web

import geb.Page

class HelloPage extends Page {

    static at = { title == 'HTML5 + REST Hello World' }

    static content = {
        name { $('#name') }

        sayHello { $('#sayHello') }

        result { $('#result') }
    }

    void enterName(String enterName) {
        name = enterName

        sayHello.click()
    }

    void ensureNameIsDisplayed(String name) {
        waitFor {
            result.text().contains(name)
        }
    }
}
