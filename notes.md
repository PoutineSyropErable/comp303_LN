For abstract decorators, just implement a decorator which have a no op. So
implement the interface, and for the method exposed by the interface, just do:
method() { return aElement.method(); }

Then you can extend the abstract decorator. Why do it, well, it's a boilerplate
investement. More boilerplate for the abstract decorator, but the specific
decorator will just have to do super(pElement) for the constructor, and
super.method() for the other. Kinda useless, but hey...

Only use is that some method can have a default implementation.
So you skip on the boilerplate of undecorated method.

==== It allows you to make the .aElement that you are decorating private, and it doesn't need to be protected

Final has a different meaning for fields, methods, classes:
• A final field cannot be assigned a value more than once.
• A final method cannot be overridden.
• A final class cannot be inherited
