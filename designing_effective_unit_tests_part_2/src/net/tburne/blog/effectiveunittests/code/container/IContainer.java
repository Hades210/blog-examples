package net.tburne.blog.effectiveunittests.code.container;

import org.picocontainer.DefaultPicoContainer;

/**
 * Have added this interface to make the container calls more readable in the code examples
 */ 
public interface IContainer {

	public IContainer add(Class<?> cls);

	public IContainer add(Object cls);

	public <ObjectType> ObjectType get(Class<ObjectType> cls);

	public static IContainer create(){
		DefaultPicoContainer container = new DefaultPicoContainer();
		return new IContainer() {
			
			@Override
			public <ObjectType> ObjectType get(Class<ObjectType> cls) {
				return container.getComponent(cls);
			}
			
			@Override
			public IContainer add(Class<?> cls) {
				container.addComponent(cls);
				return this;
			}
			
			@Override
			public IContainer add(Object obj) {
				container.addComponent(obj);
				return this;
			}

			
		};
	}
	
}
