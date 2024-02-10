# Aspect Oriented Programming (AOP) - аспектно-ориентированное программирование
Проблемы, с которыми мы сталкиваемся:
* Переплетение бизнес-логики со служебнымфункционалом (Code tandling). 
Метод станвоится громоздким, и его основной функционал сразу не заметно.
* Разбросанность служебного функционала по всему проекту (Code scattering).
При необходимости что-то изменить вслужебном функционале,
мы должны будем делать изменения во всех классах.

**AOP** - парадигма программирования, основанная на идее разделения 
основного и служебного функционала. ***Служебный функционал записывается 
в Aspect-классы.***

В основе Aspect заключена сквозная логика (cross-cutting logic). 
Любая неосновная (не бизнес) логика называется сквозной.

К сквозному функционалу относят:
* Логирование
* Проверка прав (security check)
* Обработка транзакций
* Обработка исключений
* Кэширование 
* И т.д.

**Плюсы AOP:**
* Сквозной функционал сосредоточен в 1-м или нескольких обособленных классах.
Это позволяет лече его изменять.
* Становится легче добавлять новые сквозные работы для нашего основновного кода
или имеющиемся сквозные работы для новых классов. Это достигается благодаря 
конфигурации аспектов.
* Бизнес-код приложения избавляется от сквозного кода, становится меньше и чище.
Работать с ним становится легче.

**Минус AOP:**
* Дополнительное время на работу аспектов.

**AOP frameworks:**
* **Spring AOP**
  * Предоставляет самую распространённую и необходимую функциональность AOP
  * Простой в использовании
* **AspectJ** 
  * Предоставляет всю функциональность AOP
  * Более сложный в использовании

#### ex01
* Создаём классы:
  * `Library` - бин библиотеки,
  * `MyConfig` - класс конфигурации 
* Проставляем соответствующие аннотации.
* Создаём контекст `AnnotationConfigApplicationContext`, пробуем запустить.

Если мы хотим, чтобы при вызове `getBook()` у нас осуществлялась 
сквозная функциональность, к примеру логирование, то 
* для начала нам необходимо повесить
на конфигурационный файл аннотацию `@EnableAspectJAutoProxy`, которая
позволяет нам за кулисами использовать Spring AOP Proxy (images/01_aop.png)
```
  @Configuration
  @ComponentScan("example")
  @EnableAspectJAutoProxy
  public class MyConfig {
  
  }
```
* затем, нам нужен класс с аннотацией `@Aspect`
```agsl
@Component
@Aspect
public class LogginAspect {
  // Advice
  @Before("execution(public void getBook())") // Pointcut
  public void beforeGetBookAdvice(){ // имя метода может быть любым
    System.out.println("beforeGetBookAdvice: попытка получить книгу");
  }
}
```
## Advice
**Advice** - метод, который находится в Aspect-е и содержит сквозную логику.
Advice определяет, что и когда должно происходить.  
В идеале Advice должен быть небольшим и быстро работающим.

**Advice типы:**
* **Before** - выполняется до метода с основной логикой
* **After returning** - выполняется только после нормального
окончания метода с основной логикой
* **After throwning** - выполняется после окончания метода с основной
логикой только, если было выброшено исключение
* **After/After finally** - выполняется после окончания метода с 
основной логикой
* **Around** - выполняется до и после метода с основной логикой

### Pointcut
**Pointcut** - выражение, описывающее где должен быть применён Advice

Spring AOP использует Aspect Pointcut expression language. 
Т.е. определённые правила в написании выражений для создания Pointcut

Для описания Pointcut используется шаблон  
**execution(** modifiers-pattern?**return-type-pattern**
decclaring-type-pattern? **method-name-pattern(parameters-pattern)**
throws-pattern?)

Жирным выделены обязательные элементы шаблона, а не жирным опциональные.

Если метод будет подходить под шаблон Pointcut, 
то будет выполняться наш Advice

Указав полный путь к методу класса, Advice отработает только на нём,
`"execution(public void example.classes.Library.getBook())"`  
Соответствует методу без параметров, из класса Library 
c модификатором доступа **public**, возвращает **void**, 
именем метода **getBook** и, непринимающему параметров.
```agsl
Library library = context.getBean("libraryBean", Library.class);
library.getBook();

UniLibrary uniLibrary = context.getBean("uniLibrary",
        UniLibrary.class);
uniLibrary.getBook();

SchoolLibrary schoolLibrary = context.getBean("schoolLibrary",
        SchoolLibrary.class);
schoolLibrary.getBook();
```
Вывод:
```
beforeGetBookAdvice: попытка получить книгу
Мы берём книгу
Мы берём книгу из UniLibrary
Мы берём книгу из SchoolLibrary
```
При использовании **wildcut** (`*`) мы можем делать шаблоны более гибкими.

`"execution(public void get*())"` - Соответствует методу без параметров, 
где бы он ни находился с модификатором доступа **public**,
возвращает **void**, с именами метода, начинающимеся на **get**. 
Метод параметров не принимает.

```agsl
@Before("execution(public void get*())") // point cut - когда должен выполниться сквозной код
public void beforeGetBookAdvice(){
  System.out.println("beforeGetBookAdvice: попытка получить книгу");
}
```

Вызываем методы:

```agsl
UniLibrary uniLibrary = context.getBean("uniLibrary",
          UniLibrary.class);
uniLibrary.getBook();
uniLibrary.returnBook();
uniLibrary.getMagazine();

SchoolLibrary schoolLibrary = context.getBean("schoolLibrary",
        SchoolLibrary.class);
schoolLibrary.getBook();
```

Вывод:

```
beforeGetBookAdvice: попытка получить книгу
Мы берём книгу из UniLibrary
Мы возвращаем книгу в UniLibrary
beforeGetBookAdvice: попытка получить книгу
Мы берём журнал из UniLibrary
beforeGetBookAdvice: попытка получить книгу
Мы берём книгу из SchoolLibrary
```

`"execution(public * get*())"` - шаблон не учитывает возвращаемое
значение метода.

`"execution(* *())"` - соответствует методу с любым модификатором 
доступа (потому что он необязательный, и его можно опустить), 
метод может находиться где угодно и иметь любое имя. 
Метод должен быть без параметров.

`"execution(public void getBook(String))"` - -//-, **реагирует на метод,
который принимает один параметр типа String**.

`"execution(* *(*))"` - любой модификатор, любой return, любое имя и место,
**любой один параметр**.

`"execution(* *(..))"` - любой модификатор, любой return, любое имя и место,
любое количество любых типов параметров.

#### ex03
Для того, чтобы указать в шаблоне объект како-то класса, нам нужно
указать его полный адрес.
```agsl
// полный адрес книги в параметрах метода
@Before("execution(public void getBook(example.classes.Book))")  
public void beforeGetBookAdvice(){
  System.out.println("beforeGetBookAdvice: попытка получить книгу");
}
```

Создаём два бина, и передаём бин книги в метод библиотеки

```agsl
Book book = context.getBean("book", Book.class);
Library library = context.getBean("libraryBean", Library.class);
library.getBook(book);
```
### Объявление Pointcut
#### ex04
Воспользуемся двумя Advice
```agsl
//ex03
@Before("execution(* get*())") // point cut - когда должен выполниться сквозной код
public void beforeGetLoggingAdvice(){
  System.out.println("beforeGetLoggingAdvice: попытка получить книгу/журнал");
}
@Before("execution(* get*())") // point cut - когда должен выполниться сквозной код
public void beforeGetSecurityAdvice(){
  System.out.println("beforeGetSecurityAdvice: проверка прав на получение " +
          "книги/журнала");
}
```
```agsl
Library library = context.getBean("libraryBean", Library.class);
library.getBook();
library.getMagazine();
```

Вывод:

```agsl
beforeGetLoggingAdvice: попытка получить книгу/журнал
beforeGetSecurityAdvice: проверка прав на получение книги/журнала
Мы берём книгу
beforeGetLoggingAdvice: попытка получить книгу/журнал
beforeGetSecurityAdvice: проверка прав на получение книги/журнала
Мы берём журнал
```

Для того, чтобы не пользоваться copy-paste, когда для нескольких
Advice подходит один и тот же Pointcut, есть возможность
объявить данный Pointcut и затем использовать его несколько раз.

Объявление:
`@Pointcut("pointcut_expression") 
private void pointcut_referece(){}`

Использование: 
`@Before("pointcut_reference()")
public void advice_name(){some code}`

Пример:
```agsl
  // Объявление Pointcut
@Pointcut("execution(* get*())")
private void allGetMethods(){}

@Before("allGetMethods()") // вставляем ссылку на объявленный Pointcut
public void beforeGetLoggingAdvice(){
  System.out.println("beforeGetLoggingAdvice: попытка получить книгу/журнал");
}
@Before("allGetMethods()") 
public void beforeGetSecurityAdvice(){
  System.out.println("beforeGetSecurityAdvice: проверка прав на получение " +
          "книги/журнала");
}
```

**Плюсы объявления Pointcut:**
* Возможность использования созданного Pointcut для множества Advice-ов
* Возможность быстрого изменения Pointcut expression для множества Advice-ов
* Возможность комбинирования Pointcut-ов

#### Комбинирование Pointcut-ов
Pointcut-ы можно комбинировать через логические операторы (&&, ||, !)

**Пример 1**

Будем писать:
* В лог 1, когда из библиотеки берут книги/журналы.
* В лог 2, когда в библиотеку возвращают книги/журналы.
* В лог 3, когда или берут, или возвращают.
* Действие с добавлением книги/журналы мы не отслеживаем

Создаём три Pointcut, причём третий будет комбинацией
первых двух через логическое или

```agsl
  @Pointcut("execution(* example.classes.Library.get*())")
  private void allGetMethodsGetFromLibrary(){}

  @Pointcut("execution(* example.classes.Library.return*())")
  private void allReturnMethodsGetFromLibrary(){}

  @Pointcut("allGetMethodsGetFromLibrary() " +
          "|| execution(* example.classes.Library.return*())")
  private void allGetAndReturnMethodsGetFromLibrary(){}

  @Before("allGetMethodsGetFromLibrary()")
  public void beforeGetLoggingAdvice(){
    System.out.println("beforeGetLoggingAdvice: лог №1");
  }

  @Before("allReturnMethodsGetFromLibrary()")
  public void beforeReturnLoggingAdvice(){
    System.out.println("beforeReturnLoggingAdvice: лог №2");
  }

  @Before("allGetAndReturnMethodsGetFromLibrary()")
  public void beforeGetAndReturnLoggingAdvice(){
    System.out.println("beforeReturnLoggingAdvice: лог №3");
  }
```

```agsl
library.getBook();
library.returnBook();
library.addBook();
library.getMagazine();
```

Вывод:
```
Мы берём книгу
beforeReturnLoggingAdvice: лог №3
beforeReturnLoggingAdvice: лог №2
Мы возвращаем книгу
Мы добавляем книгу
beforeReturnLoggingAdvice: лог №3
beforeGetLoggingAdvice: лог №1
Мы берём журнал
```

**Пример 2**

Будем писать в лог 4:
* Вызов любых методов кроме возврата журналов.

Создаём три Pointcut:
* Первый отслеживает вызовы всех методов (exec1)
* Второй отслеживает вызов `returnMagazine()` (exec2)
* Третий отслеживает вызовы всех методов 
за исключением возврата журналов (exec1 && !exec2)

```agsl
@Pointcut("execution(* example.classes.Library.*(..))")
private void allMethodsFromLibrary(){}

@Pointcut("execution(public void example.classes.Library.returnMagazin())")
private void returnMagazineFromLibrary(){}

// (exec1 && !exec2)
@Pointcut("allMethodsFromLibrary() " +
      "&& !returnMagazineFromLibrary())")
private void allMethodsExceptReturnFromLibrary(){}

@Before("allMethodsExceptReturnFromLibrary()")
public void beforeAllMethodsExceptReturnFromLibraryAdvice(){
    System.out.println("beforeGetLoggingAdvice: лог №4");
}
```

```agsl
library.getBook();
library.returnBook();
library.addBook();
library.getMagazine();
library.returnMagazin();
library.addBook();
```

Вывод:

```
beforeAllMethodsExceptReturnFromLibraryAdvice: лог №4
Мы берём книгу
beforeAllMethodsExceptReturnFromLibraryAdvice: лог №4
Мы возвращаем книгу
beforeAllMethodsExceptReturnFromLibraryAdvice: лог №4
Мы добавляем книгу
beforeAllMethodsExceptReturnFromLibraryAdvice: лог №4
Мы берём журнал
Мы возвращаем журнал
beforeAllMethodsExceptReturnFromLibraryAdvice: лог №4
Мы добавляем книгу
```

## Порядок выполнения Aspect-ов

Если при вызове одного метода с бизнес-логикой срабатывают
несколько Advice-ов, то нет никакой гарантии в порядке выполнения
этих Advice-ов.

Для соблюдения порядка такие Advice-ы нужно распределять по
отдельным упорядоченными Aspect-ам, и добавить аннотацию  `@Order`

```agsl
@Component
@Aspect
@Order(1)
public class LoggingAspect {
```

* В качестве порядковых номеров используются int числа.
* Могут быть отрицательными.
* Лучше использовать с разрывом в десяток (а то и больше), чтобы в случае
если нам понадобиться добавить новые Aspect-ы, не приходилось менять порядок,
а были свободные номера.

Чтобы аспекты могли обращаться к одним и тем же Pointcut,
Pointcut-ы необходимо вынести в отдельный класс.

```agsl
public class MyPointcuts {
@Pointcut("execution(* example.classes.Library.*(..))")
public void allMethodsFromLibrary(){}
}
```

при обращении к Pointcut необходимо прописывать полный
путь к методу-ссылке.

```agsl
@Component
@Aspect
@Order(1)
public class LoggingAspect {

  // ex07
  @Before("example.aspects.MyPointcuts.allGetMethods()")
  public void beforeAllGetLoggingAdvice(){
    System.out.println("beforeAllGetLoggingAdvice: лог №5");
  }
}
```

```agsl
library.getBook();
library.returnBook();
library.addBook();
```

Вывод:

```agsl
beforeAllGetLoggingAdvice: лог №5
beforeAllGetSecurityAdvice: лог №6
Мы берём книгу
Мы возвращаем книгу
Мы добавляем книгу
```

## Join Point

#### ex08

**Join Point** - то точка/момент в выполняемой программе, 
когда следует применять Advice. Т.е. это точка переплетения 
метода с бизнес-логикой и метода со служебным функционалом.

Прописав Joint Point в параметре метода Advice, 
мы получаем доступ к информации о сигнатуре и параметрах метода 
с бизнес-логикой.

Добавим в Book новые поля, G/S к ним и переопределим `toString()`

```agsl
  @Value("Ф.М. Достоевский")
  private String author;

  @Value("1866")
  private int yearOfPublication;
```

Добавим Pointcut в класс MyPointcuts

```agsl
// отслеживаем все методы на добавление
@Pointcut("execution(* add*(..))")
public void allAddMethods(){}
```

Создадим новый Advice

```agsl
// полный путь до метода с объявленным Pointcut
@Before("example.aspects.MyPointcuts.allAddMethods()")
public void beforeAllGetLoggingAdvice(JoinPoint joinPoint){ 
    // Получаем JoinPoint (Берётся из воздуха)

// Манипуляции с JoinPoint
  MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

  System.out.println("methodSignature = " + methodSignature);
  System.out.println("methodSignature.getMethod() = " + methodSignature.getMethod());
  System.out.println("methodSignature.getReturnType = " + methodSignature.getReturnType());
  System.out.println("methodSignature.getName() = " + methodSignature.getName());

  if(methodSignature.getName().equals("addBook")){
  
    Object[] args = joinPoint.getArgs();

    for (Object arg : args) {

      if(arg instanceof Book) {
        Book newBook = (Book) arg;
        System.out.println(newBook);
      } else if (arg instanceof String){
        System.out.println("Книгу добавляет: " + arg);
      }
      
    }
  }
}
```

Вызываем два метода на добавление

```agsl
    Book book = context.getBean("bookBean", Book.class);

    Library library = context.getBean("libraryBean", Library.class);

    library.addBook("Sergey", book);
    System.out.println("------------------");
    library.addMagazin();
```

Вывод:

```
methodSignature = void example.classes.Library.addBook(String,Book)
methodSignature.getMethod() = public void example.classes.Library.addBook(java.lang.String,example.classes.Book)
methodSignature.getReturnType = void
methodSignature.getName() = addBook
Книгу добавляет: Sergey
example.classes.Book@2b72cb8a
Sergey добавил книгу Преступление и наказание
------------------
methodSignature = void example.classes.Library.addMagazin()
methodSignature.getMethod() = public void example.classes.Library.addMagazin()
methodSignature.getReturnType = void
methodSignature.getName() = addMagazin
Мы добавляем журнал
```

## Advice @Befor

Выполняется до начала метода.

## Advice @AfterReturning
Выполняется только после нормального 
окончания метода с основной логикой (Не выброшено исключение)

Создадим следующие классы:
* Student (String surname, int course, double evgGrade), G/S, toStrong()
* University (List<Student>), getStudents() - класс компонент
* UniversityLoggingAspect - компонент и аспект

```agsl
@Before("execution(* getStudents())")
public void beforeGetStudentsLoggingAdvice(){
  System.out.println("beforeGetStudentsLoggingAdvice: логируем получение " +
          "списка студентов перед методом getStudents");
}

@AfterReturning(pointcut = "execution(* getStudents())",
    returning = "students")
public void afterReturningGetStudentsLoggingAdvice(List<Student> students){
  Student student = students.get(0);

  student.setSurname("newName");
  student.setAvgGrade(0);
  student.setAvgGrade(0);

  System.out.println("afterReturningGetStudentsLoggingAdvice: логируем получение " +
          "списка студентов после работы метода getStudents");
}
```

Вызов метода:

```agsl
Univesity univesity = context.getBean("univesity", Univesity.class);
univesity.init();

List<Student> students = univesity.getStudents();
System.out.println(students);
```

Вывод:

```agsl
beforeGetStudentsLoggingAdvice: логируем получение списка студентов перед методом getStudents
Список студентов:
[Student{surname='Shkaev', course=4, avgGrade=10.0}, Student{surname='Sidorov', course=3, avgGrade=9.7}, Student{surname='Ivanov', course=2, avgGrade=7.1}]
afterReturningGetStudentsLoggingAdvice: логируем получение списка студентов после работы метода getStudents
[Student{surname='newName', course=4, avgGrade=0.0}, Student{surname='Sidorov', course=3, avgGrade=9.7}, Student{surname='Ivanov', course=2, avgGrade=7.1}]
```

На что надо обратить внимание:
* Работает сначала `@Before`
* Затем отрабатывает `getStudents()`, в теле которого
выводится список студентов. После этот же список возвращает.
* Перед тем как переменной присвоится ссылка на запрашиваемый список
сработает `@AfterReturning` и перехватит результат. Для этого нам
потребуется указать `returning = "students"` и одноимённый параметр 
в методе `Advice`.
* В теле метода с `@AfterReturning` вносит изменения первого элемента
списка и завершает работу.
* Переменная получает изменённый результат работы метода.

## Advice @AfterThrowning
выполняется после окончания работы метода, 
если в нём было выброшено исключение.

```agsl
@AfterThrowing("execution(* getStudents())")
public void AfterThrowingGetStudentsLoggingAdvice(){
  System.out.println("AfterThrowingGetStudentsLoggingAdvice: " +
          "логируем выброс исключения");
}
```

В методе `getStudents()` выбрасывается исключение

```agsl
 public List<Student> getStudents(){
   System.out.println("Список студентов:");
    
    // Выбрсоится исключение, т.к. у нас всего 3 студента
   System.out.println(students.get(3));

   System.out.println(students);
   return students;
 }
 
```
Вывод при попытке получить список студентов:

```
Список студентов:
AfterThrowingGetStudentsLoggingAdvice: логируем выброс исключения
Exception in thread "main" java.lang.IndexOutOfBoundsException: Index 3 out of bounds for length 3
	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
```

Мы можем обработать выброшенное исключение
```agsl
try {
  List<Student> students = univesity.getStudents();
  System.out.println(students);
}catch (Throwable e){
  System.out.println("Поймали " + e);
}
```

а также получить доступ к самому исключению прописав в Advice `throwing`

```agsl
@AfterThrowing(pointcut = "execution(* getStudents())",
  throwing = "exception")
public void AfterThrowingGetStudentsLoggingAdvice(Throwable exception){
  System.out.println("AfterThrowingGetStudentsLoggingAdvice: " +
          "логируем выброс исключения " + exception);
}
```

Вывод:

```
Список студентов:
AfterThrowingGetStudentsLoggingAdvice: логируем выброс исключения java.lang.IndexOutOfBoundsException: Index 3 out of bounds for length 3
Поймали java.lang.IndexOutOfBoundsException: Index 3 out of bounds for length 3
```

Advice `@AfterThrowning` не влияет на протекание программы
при выбрасывании исключения. Можно получить доступ к исключению,
которое выбросилось из метода с основной логикой, можем предпринять
какие-нибудь действия (залогировать например), но не можем
обрабатать его.

## Advice @After

Выполняется после окончания метода с основной логикой вне зависимости
от того, завершается ли метод нормально или выбрасывается исключение.

С помощью `@After` невозможно:
* Получить доступ к исключению, которое выбросилось из метода
с основной логикой.
* Получить доступ к возвращаемому методом результату.

## Advice @Around

Выполняется до и после метода с основной логикой.

С помощью `@Around@` возможно:
* Произвести какие-либо действия до работы target метода;
* Произвести какие-либо действия после работы target метода;
* Получить резальтат работы target метода/изменить его;
* Предпринять какие-либо действия если из target метод 
выбрасывается исключение (в том числе и обработать их).

#### ex11

Добавляем метод в класс `Library`. Метод возвращает строку.

```agsl
public String returnBookEx11(){
  System.out.println("returnBookEx11: Мы возвращаем книгу");
  return "Война и мир";
}
```

Создаём новый Advice с аннотацией `@Around`

```agsl
@Around("execution(public String returnBookEx11())")
// раз шаблон возвращает String, то и наш Advice тоже должен возвращать String
// но т.к. String это Object, то можно так.
public Object aroundReturnBookLoggingAdvice(
  ProceedingJoinPoint proceedingJoinPoint)  { 
  // Метод принимает из воздуха ProceedingJoinPoint
  // нужен для связи с target методом, и в ручном режиме запускает его.
  // Если не запустить target метод через proceedingJoinPoint.proceed(),
  // то target метод не отработает, а Advice вернёт null
  
  // Пример работы до вызовава target метода
  System.out.println("aroundReturnBookLoggingAdvice: в библиотеку пытаются вернуть книгу");
  
  // Запуск target метода может выбросить исключение.
  // Мы можем пробросить, а можем обработать его в самом Advice-методе
  try {
    
    //Запускаем target метод
    Object targetMethodResult = proceedingJoinPoint.proceed();

  } catch (Throwable e){
    System.out.println("Поймали " + e); // для примера работы с @Around
  }

  // пример работы после target метода
  System.out.printf("returnBookEx11() - выполнил работу за %d милисекунд\n", time);
  
  // мы можем вернуть targetMethodResult, можем его изменить, 
  // а можем вернуть дичь. Конечно же вернём дичь.
  return "А вернули, не пойми что";
}
```

Вызываем наш `returnBookEx11()`

```agsl
Library library = context.getBean("libraryBean", Library.class);

String book = library.returnBookEx11();
System.out.println("Мы вернули " + book);
```

Вывод:

```
aroundReturnBookLoggingAdvice: в библиотеку пытаются вернуть книгу
returnBookEx11: Мы возвращаем книгу
aroundReturnBookLoggingAdvice: в библиотеку успешно вернули книгу
returnBookEx11() - выполнил работу за 0 милисекунд
Мы вернули А вернули, не пойми что
```

## Как создать свою аннотацию Aspect-а

#### ex12
Аннотацию, которую мы собираемся создать, мы будем использовать 
для регистрации количества времени, необходимого для выполнения 
метода. Давайте создадим нашу аннотацию:

```agsl
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {

}
```

Аннотация `@Target` сообщает нам, где наша аннотация будет 
применима к (здесь мы используем) `ElementType.Method`, что означает,
что он будет работать только с методами. Если бы мы попытались 
использовать аннотацию в другом месте, наш код не смог бы 
скомпилироваться. Такое поведение имеет смысл, так как наша 
аннотация будет использоваться для регистрации времени выполнения
метода.

Аннотация `@Retention` просто указывает, будет ли аннотация 
доступна для JVM во время выполнения или нет. По умолчанию это 
не так, поэтому Spring AOP не сможет увидеть аннотацию. 
Вот почему он был переконфигурирован.

#### Создание нашего аспекта
Теперь у нас есть аннотация, давайте создадим наш аспект. 

```agsl
@Aspect
@Component
public class ExampleAspect {

}
```

Мы также включили аннотацию `@Component`, так как наш класс 
также должен быть bean-компонентом Spring, чтобы его можно было
обнаружить. По сути, это класс, в котором мы будем реализовывать
логику, которую мы хотим внедрить в нашу пользовательскую 
аннотацию.

#### Создание нашего Pointcut и Advice
Теперь давайте создадим наш Pointcut и Advice. Это будет 
аннотированный метод, живущий в нашем аспекте:

```agsl
// Реагирует на метод с нашей аннотацие @LogExecutionTime
@Around("@annotation(LogExecutionTime)")
public Object loggingExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();

    Object proceed = joinPoint.proceed();

    long executionTime = System.currentTimeMillis() - start;

    System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
    return proceed;
}
```

Сам метод `loggingExecutionTime()` является нашим советом. 
Это будет исполняемый метод нашей аннотацией `@LogExecutionTime`.

Теперь давайте попробуем аннотировать метод (в классе `Library`) с помощью 
`@LogExecutionTime`, а затем выполнить его, чтобы посмотреть, 
что произойдет. Обратите внимание, что для правильной работы 
это должен быть Spring Bean:

```agsl
@LogExecutionTime
public void work() throws InterruptedException {
    Thread.sleep(2000);
}
```

Вызываем метод:

```agsl
Library library = context.getBean("libraryBean", Library.class);
library.work();
```

Вывод:

```
void example.classes.Library.work() executed in 2002ms
```








