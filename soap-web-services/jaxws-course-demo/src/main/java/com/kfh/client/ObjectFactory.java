
package com.kfh.client;


import javax.xml.namespace.QName;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.kfh.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AddCourse_QNAME = new QName("http://service.kfh.com/", "addCourse");
    private final static QName _AddCourseResponse_QNAME = new QName("http://service.kfh.com/", "addCourseResponse");
    private final static QName _GetAllCourses_QNAME = new QName("http://service.kfh.com/", "getAllCourses");
    private final static QName _GetAllCoursesResponse_QNAME = new QName("http://service.kfh.com/", "getAllCoursesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.kfh.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddCourse }
     * 
     */
    public AddCourse createAddCourse() {
        return new AddCourse();
    }

    /**
     * Create an instance of {@link AddCourseResponse }
     * 
     */
    public AddCourseResponse createAddCourseResponse() {
        return new AddCourseResponse();
    }

    /**
     * Create an instance of {@link GetAllCourses }
     * 
     */
    public GetAllCourses createGetAllCourses() {
        return new GetAllCourses();
    }

    /**
     * Create an instance of {@link GetAllCoursesResponse }
     * 
     */
    public GetAllCoursesResponse createGetAllCoursesResponse() {
        return new GetAllCoursesResponse();
    }

    /**
     * Create an instance of {@link Course }
     * 
     */
    public Course createCourse() {
        return new Course();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCourse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddCourse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.kfh.com/", name = "addCourse")
    public JAXBElement<AddCourse> createAddCourse(AddCourse value) {
        return new JAXBElement<AddCourse>(_AddCourse_QNAME, AddCourse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCourseResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AddCourseResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.kfh.com/", name = "addCourseResponse")
    public JAXBElement<AddCourseResponse> createAddCourseResponse(AddCourseResponse value) {
        return new JAXBElement<AddCourseResponse>(_AddCourseResponse_QNAME, AddCourseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllCourses }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllCourses }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.kfh.com/", name = "getAllCourses")
    public JAXBElement<GetAllCourses> createGetAllCourses(GetAllCourses value) {
        return new JAXBElement<GetAllCourses>(_GetAllCourses_QNAME, GetAllCourses.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllCoursesResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAllCoursesResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.kfh.com/", name = "getAllCoursesResponse")
    public JAXBElement<GetAllCoursesResponse> createGetAllCoursesResponse(GetAllCoursesResponse value) {
        return new JAXBElement<GetAllCoursesResponse>(_GetAllCoursesResponse_QNAME, GetAllCoursesResponse.class, null, value);
    }

}
