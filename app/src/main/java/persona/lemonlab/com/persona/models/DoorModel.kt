package persona.lemonlab.com.persona.models

/**
 * Created by HP on 01/09/2018.
 */

data class Question(var QuestionText:String,
                    var a:HashMap<String,Pair<Int,Boolean>>,
                    var b:HashMap<String,Pair<Int,Boolean>>,
                    var c:HashMap<String,Pair<Int,Boolean>>,
                    var d:HashMap<String,Pair<Int,Boolean>>)

class DoorModel {

    companion object{
        var Trov:Int = 0
        var Trav:Int = 0 // this is the boolean var in Question Data Class

        // hashMapOf(Pair("",Pair(0,true)))

        var addtionInformationAboutDoors = ArrayList<String>().apply{
            add("كيف هي ثقتنا بنفسنا عند اتخاذ قراراتنا، وكيف نختار الطريقة الأفضل في عمل ما")
            add("كيف نصنعُ القرارات ونتعامل مع المشاعر")
            add("كيف ترى وتتفاعل مع الأشياء والأنشطة والأشخاص")
            add("كيف ترى العالم وتعالج المعلومات")
        }

        // Topic 01

        var topic_one = ArrayList<Question>().apply{
            // juas -> trav

            // 01
            add(Question("اشترى صديقٌ لك نفس لباسك في مناسبة ما",
                    a = hashMapOf(Pair("مَن يهتم",Pair(3,true))),
                    b = hashMapOf(Pair("لمَ تلبس مثل ما ألبس؟",Pair(3,false))),
                    c = hashMapOf(Pair("تشعر بالضيق داخليًا",Pair(3,false))),
                    d = hashMapOf(Pair("لا شيء",Pair(0,false)))
            ))

            // 02
            add(Question("دورك بين أصدقائك",
                    a = hashMapOf(Pair("تساعد غيرك في اتخاذ قراراتهم",Pair(5,true))),
                    b = hashMapOf(Pair("الرئيس",Pair(5,true))),
                    c = hashMapOf(Pair("تتابع بصمت",Pair(5,false))),
                    d = hashMapOf(Pair("جميعنا بنفس الأدوار",Pair(5,false)))
            ))

            // 03
            add(Question("تتكلم عن الناس في غيابهم",
                    a = hashMapOf(Pair("حسب الشخص",Pair(5,false))),
                    b = hashMapOf(Pair("وعند حضورهم",Pair(3,true))),
                    c = hashMapOf(Pair("أحاول ترك هذه العادة",Pair(3,false))),
                    d = hashMapOf(Pair("طبعا بوضوح",Pair(3,false)))
            ))

            // 04
            add(Question("في يوم الأحد أنت ترتدي",
                    a = hashMapOf(Pair("لونًا فاقعًا",Pair(3,false))),
                    b = hashMapOf(Pair("الأسود مثل أرواح البشر",Pair(3,true))),
                    c = hashMapOf(Pair("أي شيء",Pair(3,false))),
                    d = hashMapOf(Pair("لا أهتم",Pair(3,true)))
            ))

            // 05
            add(Question("تمشي مع التيار أم عكس التيار",
                    a = hashMapOf(Pair("مع التيار",Pair(5,false))),
                    b = hashMapOf(Pair("لا أمشي",Pair(5,true))),
                    c = hashMapOf(Pair("عكس التيار",Pair(5,false))),
                    d = hashMapOf(Pair("حسب وجهة التيار",Pair(5,true)))
            ))

            // 06
            add(Question("كيف أنت في المنافسة",
                    a = hashMapOf(Pair("لا أحد يتغلب علي",Pair(3,false))),
                    b = hashMapOf(Pair("حسب المنافسة على ماذا",Pair(3,false))),
                    c = hashMapOf(Pair("لا أنافس أحدًا",Pair(3,true))),
                    d = hashMapOf(Pair("أنافس نفسي",Pair(3,true)))
            ))

            // 07
            add(Question("حصل صديقك على تسريحة شعر جديدة لكنها ليست جميلة، يسألك عن رأيك",
                    a = hashMapOf(Pair("تكون صادقًا، وتقول الحقيقة",Pair(3,true))),
                    b = hashMapOf(Pair("تقول أنها تسريحة جميلة ورائعة",Pair(3,false))),
                    c = hashMapOf(Pair("تسأله عن رأيه هو في هذه التسريحة",Pair(3,true))),
                    d = hashMapOf(Pair("تقول أنها تسريحة جيدة، لكن السابقة أفضل",Pair(3,false)))
            ))

            // 08
            add(Question("هل أنت مشهور",
                    a = hashMapOf(Pair("بشكل واضح، نعم",Pair(3,false))),
                    b = hashMapOf(Pair("من يهتم",Pair(3,true))),
                    c = hashMapOf(Pair("لستُ مشهورًا",Pair(0,false))),
                    d = hashMapOf(Pair("مستحيل",Pair(3,true)))
            ))

            // 09
            add(Question("هل تحب الدراسة",
                    a = hashMapOf(Pair("نعم، الدراسة ممتعة",Pair(0,false))),
                    b = hashMapOf(Pair("لا، أدرس من أجل الاجتماع بأصدقائي",Pair(3,false))),
                    c = hashMapOf(Pair("أنتظر يوم تخرجي بفارغ الصبر",Pair(0,false))),
                    d = hashMapOf(Pair("أيًا كان",Pair(3,true)))
            ))

            // 10
            add(Question("ماذا عن التخييم",
                    a = hashMapOf(Pair("أحب التخييم",Pair(3,true))),
                    b = hashMapOf(Pair("إذا كانت معي جميع أغراضي ومستلزماتي، نعم",Pair(3,true))),
                    c = hashMapOf(Pair("لا، أبدًا",Pair(0,false))),
                    d = hashMapOf(Pair("هل النوم في كوخ يعتبر تخييمًا؟",Pair(3,false)))
            ))

            // 11
            add(Question("ومواقع التواصل؟",
                    a = hashMapOf(Pair("أهتم بمواقع التواصل قليلًا",Pair(3,true))),
                    b = hashMapOf(Pair("لدي انستغرام لكن قلّ ما أنشر أي شيء",Pair(3,true))),
                    c = hashMapOf(Pair("أكره مواقع التواصل",Pair(3,true))),
                    d = hashMapOf(Pair("أحب مواقع التواصل وأستخدم معظمها بشكل يومي",Pair(4,false)))
            ))

            // 12
            add(Question("هل لديك أصدقاء محبطون",
                    a = hashMapOf(Pair("لديّ بعض المعارف ولا أعتبرهم أصدقاء",Pair(3,true))),
                    b = hashMapOf(Pair("لا أعرف أحدًا",Pair(3,false))),
                    c = hashMapOf(Pair("نعم، لهذا أكون حذرًا عن الحديث عن أهدافي",Pair(3,true))),
                    d = hashMapOf(Pair("ربما",Pair(3,false)))
            ))

            // 13
            add(Question("من عشرة، كم أنت رومنسي",
                    a = hashMapOf(Pair("4",Pair(0,false))),
                    b = hashMapOf(Pair("2.5",Pair(0,false))),
                    c = hashMapOf(Pair("0",Pair(0,false))),
                    d = hashMapOf(Pair("10",Pair(0,false)))
            ))

            // 14
            add(Question("هل تتأخر عن مواعيدك",
                    a = hashMapOf(Pair("دائمًا",Pair(3,false))),
                    b = hashMapOf(Pair("أحيانًا",Pair(3,false))),
                    c = hashMapOf(Pair("حسب الموعد",Pair(3,false))),
                    d = hashMapOf(Pair("أبدًا",Pair(3,false)))
            ))

            // 16
            add(Question("من 10، ما نسبة اختلافك عن غيرك",
                    a = hashMapOf(Pair("10 طبعًا",Pair(3,true))),
                    b = hashMapOf(Pair("4.5",Pair(0,false))),
                    c = hashMapOf(Pair("7",Pair(0,false))),
                    d = hashMapOf(Pair("2",Pair(3,false)))
            ))

            // 17
            add(Question("أفضل صفة تودها في أبنائك",
                    a = hashMapOf(Pair("الهدوء",Pair(0,false))),
                    b = hashMapOf(Pair("الطاقة والحركة",Pair(0,false))),
                    c = hashMapOf(Pair("اللطف والبراءة",Pair(0,false))),
                    d = hashMapOf(Pair("الذكاء",Pair(0,false)))
            ))

            // 18
            add(Question("هل تعتقد أنه بإمكانك تغيير العالم",
                    a = hashMapOf(Pair("نعم، أستطيع ذلك",Pair(3,true))),
                    b = hashMapOf(Pair("لا، لستُ مميزًا بشيء",Pair(3,false))),
                    c = hashMapOf(Pair("يمكنني المساهمة في مجتمعي",Pair(3,false))),
                    d = hashMapOf(Pair("تغيير عالمي، نعم",Pair(3,true)))
            ))

            // 19
            add(Question("ما رأيك بالمدن الكبيرة المكتظة بالسكان",
                    a = hashMapOf(Pair("أحب المناطق المنعزلة",Pair(3,true))),
                    b = hashMapOf(Pair("ليست من نوعي المفضل",Pair(3,true))),
                    c = hashMapOf(Pair("لا بأس بها",Pair(3,true))),
                    d = hashMapOf(Pair("عظيمة جدًا",Pair(4,false)))
            ))

            // 20
            add(Question("كيف تتذكر الماضي",
                    a = hashMapOf(Pair("الأشخاص",Pair(3,false))),
                    b = hashMapOf(Pair("الحقائق البسيطة",Pair(3,false))),
                    c = hashMapOf(Pair("أتذكر التفاصيل مع بعض المشاهد والأصوات",Pair(3,true))),
                    d = hashMapOf(Pair("لا أتذكر",Pair(0,false)))
            ))

            // 21
            add(Question("هل تستطيع قراءة أفكار الأشخاص من عيونهم",
                    a = hashMapOf(Pair("نعم غالبًا",Pair(0,false))),
                    b = hashMapOf(Pair("إلى حد ما",Pair(0,false))),
                    c = hashMapOf(Pair("حسب الشخص",Pair(0,false))),
                    d = hashMapOf(Pair("لا أبدًا",Pair(0,false)))
            ))

            // 22
            add(Question("إذا كنتَ في ورطة، فإنك تستخدم",
                    a = hashMapOf(Pair("عقلك للتفكير بطريقة ذكية",Pair(3,true))),
                    b = hashMapOf(Pair("جسدك لتندفع بسرعة بدون أي ضرر",Pair(3,true))),
                    c = hashMapOf(Pair("مساعدة من حولك للخروج بسلام",Pair(3,false))),
                    d = hashMapOf(Pair("تبقى في الورطة",Pair(0,false)))
            ))

            // 23
            add(Question("أفضل طريق هو",
                    a = hashMapOf(Pair("الطريق الثابت المتوازن",Pair(3,true))),
                    b = hashMapOf(Pair("الطريق المضيء السهل",Pair(3,true))),
                    c = hashMapOf(Pair("الطريق المليء بالفرص",Pair(0,false))),
                    d = hashMapOf(Pair("الطريق إلى أوروبا",Pair(3,false)))
            ))

            // 24
            add(Question("إذا حصلت على نتيجة لم ترضيك، هل ستشاركها",
                    a = hashMapOf(Pair("لا",Pair(0,false))),
                    b = hashMapOf(Pair("نعم",Pair(3,true))),
                    c = hashMapOf(Pair("طبعًا",Pair(3,true))),
                    d = hashMapOf(Pair("أبدًا",Pair(0,false)))
            ))

            // 25
            add(Question("ماذا إذا حصلت على نتيجة ترضيك، ستشاركها",
                    a = hashMapOf(Pair("لا",Pair(0,true))),
                    b = hashMapOf(Pair("نعم",Pair(3,false))),
                    c = hashMapOf(Pair("طبعًا",Pair(3,false))),
                    d = hashMapOf(Pair("أبدًا",Pair(0,true)))
            ))

            // 26
            add(Question("كيف حللت آخر مشكلة مررت بها",
                    a = hashMapOf(Pair("توقعت الحل",Pair(0,false))),
                    b = hashMapOf(Pair("آلة حاسبة",Pair(0,false))),
                    c = hashMapOf(Pair("استخدام ورقة",Pair(0,false))),
                    d = hashMapOf(Pair("طريقة غاوس",Pair(0,false)))
            ))
        }


        // Topic 01 Result

        var topic_one_result = ArrayList<String>().apply{

            add("يعتبر التنظيم أحد أهم صفاتك، العمق والشمولية في كل شيء أيضًا. تهمّك الصراحة والوضوح في الأشخاص، ولديك قدرة على تنبؤ تصرفات بعض الأفراد في ظروف ما، تفضّل الهيكلة والتخطيط لكل شيء مسبقًا." +
                    "لا تحب أن تكون خياراتك مفتوحة، بل عندك القدرة على صنع خطط طارئة في وقت قصير منعًا للمخاطرة وتجنبًا للمخاطر." +
                    "علاوة على ذلك، فإنك تقدّر أخلاق العمل، وتعتبر، وتضع مسؤولياتك وواجباتك فوق كل شيء." +
                    "أيضًا، قل ما تحتاج التأكيد والدعم من غيرك، مستويات التوتر المنخفضة لا تؤثر فيك، وبشكل عجيب، فإن أمثالك لا يفكرون كثيرًا في الماضي لأن ما قد مضى قد مضى ويقال أنه لا فائدة من التفكير فيه." +
                    "مع كل هذا والأهم، تشعر بالرضا والامتنان في معظم أوقات حياتك مما يسهّل الشعور بالسعادة، وهذا يعتبر مؤشرًا على النجاح في الحياة.")

            add("القليل من التنظيم مع بعض العفوية يبدو مناسبًا في معظم الأوقات، تخطّط لمعظم أفعالك لكن لا مشكلة إن ظهر شيء غير متوقع، تحب دفعَ نفسك لتحقيق نتائج عظيمة، تحب أن تقوم بالأشياء حسب أولويتها ولا مانع أحيانًا من بعض المخالفة لجدولك." +
                    "تهتم بالطريقة التي ينظر إليك الناس فيها، وحتى بالطريقة التي تنظر فيها لنفسك، لديك القدرة على تحمّل التوتر لدرجة معيّنة ومنعه من التأثير في حياتك اليوميّة ولكن إن سمحت لك الفرصة فإنك تقفز بعيدًا عنه بأقرب فرصة." +
                    "قد تحتاج وقتًا لتنظر وتعيد ضبط اتجاهاتك ومساراتك في الحياة وبعض المساعدة من معارفك أحيانًا مفيدة في اتخاذ قراراتك، لديك طاقة عالية وتحب العمل ورؤية النتائج على المدى الطويل، قد يراك البعض مملًا لكن من يعرفك حقًا يعرف أن لديك أهداف وطموح عظيمة، من السهل التعامل معك ولا مانع عندك من اتباع العادات والتقاليد في المجتمع." +
                    "")

            add("العفوية هي أهم قواعد الحياة بالنسبة لك، لديك مرونة واسترخاء في التعامل مع الظروف والتحديات المتوقعة وغير المتوقعة. دائمًا تبحث عن الفرص والخيارات الأفضل، ومستعد للتغيير من أجل الأفضل. يقينك بأن الحياة مليئة بالفرص هو أحد أهم أسباب نجاحك، تواجه صعوبة في وضع قرارات قد تؤثر فيك مستقبلًا." +
                    "سعادتك وسعادة من حولك مهمة بالنسبة لك، لديك وعي ذاتي وتعرف إلى أي مدى تؤثر أفعالك ومظهرك بغيرك، تمرّ بنطاق واسع من المشاعر ويحرّك هذه المشاعر النجاح الذي تسعى إليه، متعطش إلى تطوير ذاتك وتقوم بمعظم ما تقومه باحترافيّة، دائمًا تبحث عن المزيد، عن الأفضل، ولا تحب الوقوف عند محطة معيّنة إلا عندما تحتاج أن تعرف إلى أين تأخذك الحياة.")
        }

        // Topic 02

        var topic_two = ArrayList<Question>().apply {

            // feel -> trav

            // 01
            add(Question("شخصٌ يحمل سلاحًا لا يعرف كيف يستعمله أخطر من شخص يحمل سلاحًا يعرف كيف يستعمله، ما نسبة اتفاقك",
                    a = hashMapOf(Pair("0%",Pair(3,false))),
                    b = hashMapOf(Pair("20%-40%",Pair(3,false))),
                    c = hashMapOf(Pair("50%-80%",Pair(3,true))),
                    d = hashMapOf(Pair("100%",Pair(3,true)))
            ))

            // 02
            add(Question("ألف دولار مبلغ قليل إذا كان معك، كثير إذا كان دينًا عليك، ما نسبة اتفاقك",
                    a = hashMapOf(Pair("0%",Pair(3,false))),
                    b = hashMapOf(Pair("20%-40%",Pair(0,false))),
                    c = hashMapOf(Pair("50%-80%",Pair(3,true))),
                    d = hashMapOf(Pair("100%",Pair(3,true)))
            ))

            // 03
            add(Question("من السهل مواجهة",
                    a = hashMapOf(Pair("أفكار مثيرة",Pair(3,false))),
                    b = hashMapOf(Pair("ناس جدد",Pair(3,true))),
                    c = hashMapOf(Pair("مواقف غريبة",Pair(3,false))),
                    d = hashMapOf(Pair("أعمال كثيرة",Pair(3,false)))
            ))

            // 04
            add(Question("أي جزء في حياتك يهمّك بشكل أكبر",
                    a = hashMapOf(Pair("حل مشاكلك الشخصيّة",Pair(3,true))),
                    b = hashMapOf(Pair("مساعدة غيرك",Pair(0,false))),
                    c = hashMapOf(Pair("التنظيم والمنظر الشخصي",Pair(3,false))),
                    d = hashMapOf(Pair("صحتي وصحة من حولي، ومعاملتي معهم",Pair(3,true)))
            ))

            /*// 05
            add(Question("أن تنام قبل ساعة من موعد نومك المعتاد وتحصل على نوم إضافي يبدو غباء، وأن تحصل على غفوة في الصباح لخمسة دقائق أمر رهيب، ما نسبة اتفاقك",
                    a = hashMapOf(Pair("0%",Pair(0,false))),
                    b = hashMapOf(Pair("20%-40%",Pair(3,false))),
                    c = hashMapOf(Pair("50%-80%",Pair(4,true))),
                    d = hashMapOf(Pair("100%",Pair(4,true)))
            ))*/

            /*// 06
            add(Question("إذا رأى شخصٌ من القرن الماضي تأثيرات على فيديو فإنه يقول أن هذا سحر. وإذا رأى شخص من القرن الحالي سحرًا فإنه يقول أن هذه تأثيرات خاصة",
                    a = hashMapOf(Pair("صحيح",Pair(3,false))),
                    b = hashMapOf(Pair("صحيح إلى درجة ما",Pair(3,true))),
                    c = hashMapOf(Pair("غير صحيح",Pair(0,false))),
                    d = hashMapOf(Pair("القانون الثالث لآرثر كلارك: \"لا يمكن تمييز التكنولوجيا المتقدمة عن السحر\"",Pair(3,false)))
            ))*/

            // 07
            add(Question("المنطق والأسباب أهم من العواطف في القرارات المهمة، ما نسبة اتفاقك",
                    a = hashMapOf(Pair("0%",Pair(5,true))),
                    b = hashMapOf(Pair("20%-40%",Pair(3,true))),
                    c = hashMapOf(Pair("50%-80%",Pair(5,false))),
                    d = hashMapOf(Pair("100%",Pair(5,false)))
            ))

            // 08
            add(Question("المعرفة تأتي من",
                    a = hashMapOf(Pair("الكتب",Pair(3,false))),
                    b = hashMapOf(Pair("التجارب",Pair(3,false))),
                    c = hashMapOf(Pair("داخلك",Pair(3,false))),
                    d = hashMapOf(Pair("الناس",Pair(4,true)))
            ))

           /* // 09
            add(Question("تعتبر شرب الماء أمرًا مهمًا في حياتك",
                    a = hashMapOf(Pair("لا",Pair(0,false))),
                    b = hashMapOf(Pair("نعم",Pair(0,false))),
                    c = hashMapOf(Pair("من يشرب الماء في هذا العصر؟",Pair(0,false))),
                    d = hashMapOf(Pair("هل الشاي والعصير من مشتقات الماء؟",Pair(0,false)))
            ))*/

            // 10
            add(Question("في نقاش، أنت تركز على الحقيقة بغض النظر عن مشاعر الناس",
                    a = hashMapOf(Pair("0%",Pair(4,true))),
                    b = hashMapOf(Pair("20%-40%",Pair(3,true))),
                    c = hashMapOf(Pair("50%-80%",Pair(3,false))),
                    d = hashMapOf(Pair("100%",Pair(4,false)))
            ))

           /* // 11
            add(Question("أي التالية يصعب عليك تحمّله",
                    a = hashMapOf(Pair("النقد",Pair(0,false))),
                    b = hashMapOf(Pair("الملل",Pair(0,false))),
                    c = hashMapOf(Pair("مواجهة الاحتمالات اليومية",Pair(0,false))),
                    d = hashMapOf(Pair("أن تكون معرضًا للأخطار",Pair(0,false)))
            ))*/

            // 12
            add(Question("عندما تتكلم، تلاحظ أن الناس",
                    a = hashMapOf(Pair("يستمعوا لك",Pair(3,false))),
                    b = hashMapOf(Pair("يجادلونك",Pair(3,false))),
                    c = hashMapOf(Pair("يهربوا منك",Pair(3,true))),
                    d = hashMapOf(Pair("يتفقون معك بشكل كامل",Pair(0,false)))
            ))

            // 13
            add(Question("شتاء أم صيف، في غابة أم نهر؟",
                    a = hashMapOf(Pair("شتاء وغابة",Pair(0,false))),
                    b = hashMapOf(Pair("شتاء ونهر",Pair(0,false))),
                    c = hashMapOf(Pair("صيف وغابة",Pair(0,false))),
                    d = hashMapOf(Pair(" صيف ونهر",Pair(0,false)))
            ))

            // 14
            add(Question("فضولك يدفعك لاكتشاف وتجربة أشياء جديدة",
                    a = hashMapOf(Pair("0%",Pair(3,true))),
                    b = hashMapOf(Pair("20%-40%",Pair(3,true))),
                    c = hashMapOf(Pair("50%-80%",Pair(3,false))),
                    d = hashMapOf(Pair("100%",Pair(3,false)))
            ))

            // 15
            add(Question("لو كان لك الخيار للحصول على قوة خارقة",
                    a = hashMapOf(Pair("تغيير مظهرك كما تشاء",Pair(3,true))),
                    b = hashMapOf(Pair(" قراءة عقول وأفكار الناس",Pair(0,false))),
                    c = hashMapOf(Pair("الطيران",Pair(3,false))),
                    d = hashMapOf(Pair("الاختفاء",Pair(0,false)))
            ))

            // 16
            add(Question("أكثر وصف تكره أن توصف به",
                    a = hashMapOf(Pair("جاهل",Pair(3,false))),
                    b = hashMapOf(Pair("عادي",Pair(3,false))),
                    c = hashMapOf(Pair("أناني",Pair(3,true))),
                    d = hashMapOf(Pair("جبان",Pair(3,true)))
            ))

            // 17
            add(Question("دخلت مكانًا عجيبًا، أول ما تتفحصه هو",
                    a = hashMapOf(Pair("شجرة تفاح أوراقها فضية وثمرها ذهبي",Pair(3,true))),
                    b = hashMapOf(Pair("حوض سباحة يخرج منه فقاعات وكأن مخلوقًا في الأسفل",Pair(3,true))),
                    c = hashMapOf(Pair("تمثال لشخص لا تعرفه، عينه ترمش",Pair(3,false))),
                    d = hashMapOf(Pair("كتاب ذو بريق ولون عجيب",Pair(3,false)))
            ))

            // 18
            add(Question("معرفة القواعد والقوانين في مكان ما، تساهم في تقليل الأخطاء",
                    a = hashMapOf(Pair("0%",Pair(0,false))),
                    b = hashMapOf(Pair("20%-40%",Pair(3,true))),
                    c = hashMapOf(Pair("50%-80%",Pair(3,false))),
                    d = hashMapOf(Pair("100%",Pair(3,false)))
            ))

            // 19
            add(Question("من السهل أن تصبح متحمسًا لحدث ما",
                    a = hashMapOf(Pair("0%",Pair(0,false))),
                    b = hashMapOf(Pair("20%-40%",Pair(3,false))),
                    c = hashMapOf(Pair("50%-80%",Pair(3,true))),
                    d = hashMapOf(Pair("100%",Pair(3,true)))
            ))

            // 20
            add(Question("يمكن القول أن أمر البشرية يهمك",
                    a = hashMapOf(Pair("0%",Pair(3,false))),
                    b = hashMapOf(Pair("20%-40%",Pair(3,false))),
                    c = hashMapOf(Pair("50%-80%",Pair(4,true))),
                    d = hashMapOf(Pair("100%",Pair(4,true)))
            ))

            // 21
            add(Question("تخصصك الجامعي يعطيك:",
                    a = hashMapOf(Pair("العلم الكثير",Pair(3,false))),
                    b = hashMapOf(Pair("الخبرة والتجارب العظيمة",Pair(3,false))),
                    c = hashMapOf(Pair("الاصدقاء والمعارف الجدد",Pair(3,true))),
                    d = hashMapOf(Pair("ذكريات رهيبة",Pair(3,true)))
            ))

            // 22
            add(Question("الكثير من الأجهزة الذكية، والكثير من الناس الأغبياء",
                    a = hashMapOf(Pair("0%",Pair(3,true))),
                    b = hashMapOf(Pair("20%-40%",Pair(0,false))),
                    c = hashMapOf(Pair("50%-80%",Pair(0,false))),
                    d = hashMapOf(Pair("100%",Pair(3,false)))
            ))

            // 23
           /* add(Question("قد يتجمد الحاسوب إذا ارتفعت درجة حرارته",
                    a = hashMapOf(Pair("طبعًا",Pair(3,false))),
                    b = hashMapOf(Pair("أحيانًا",Pair(3,false))),
                    c = hashMapOf(Pair("لا يمكن",Pair(3,true))),
                    d = hashMapOf(Pair(" حسب درجة حرارته",Pair(0,false)))
            ))*/

            // 24
            add(Question("الناس الذي يأكلون العلكة ناس مزعجون",
                    a = hashMapOf(Pair("صحيح",Pair(0,false))),
                    b = hashMapOf(Pair(" ليس صحيح",Pair(0,false))),
                    c = hashMapOf(Pair("والذين يأكلون التفاح مجرمون",Pair(3,true))),
                    d = hashMapOf(Pair("الجميع هنا يأكل العلكة",Pair(3,true)))
            ))

            // 25
            add(Question("الفوز بالمرتبة الأخيرة أفضل من الفوز بالمرتبة الثانية",
                    a = hashMapOf(Pair("نعم",Pair(3,false))),
                    b = hashMapOf(Pair("إن الصفر من شيَم الرجال",Pair(3,true))),
                    c = hashMapOf(Pair("ليس دائماً",Pair(3,true))),
                    d = hashMapOf(Pair("في السباق نعم",Pair(3,true)))
            ))
            // 26
            add(Question("البشر يريدون تحقيق السلام ويتحاربون من أجل ذلك",
                    a = hashMapOf(Pair("0%",Pair(3,true))),
                    b = hashMapOf(Pair("20%-40%",Pair(3,true))),
                    c = hashMapOf(Pair("50%-80%",Pair(3,false))),
                    d = hashMapOf(Pair("100%",Pair(3,false)))
            ))

            // 27
            add(Question("شاشة هاتف ذكي سوداء أفضل من الكاميرا الأمامية كمرآة",
                    a = hashMapOf(Pair("صحيح",Pair(3,true))),
                    b = hashMapOf(Pair("علميًا،كلامك خاطئ",Pair(3,false))),
                    c = hashMapOf(Pair(" أتفق مع 50% من هذا",Pair(3,false))),
                    d = hashMapOf(Pair("لا، الكاميرا أفضل",Pair(0,false)))
            ))

            // 28
            add(Question("طبيب الاعصاب، مثل مضاد الفيروسات في الكمبيوتر، لا يقوم بشيء فعليًا، بل يجعل الناس يشعرون بتحسن",
                    a = hashMapOf(Pair("0%",Pair(0,true))),
                    b = hashMapOf(Pair("20%-40%",Pair(0,false))),
                    c = hashMapOf(Pair("50%-80%",Pair(3,false))),
                    d = hashMapOf(Pair("100%",Pair(3,false)))
            ))
            // 29
            add(Question("يمكنك توصيل شاحن هاتفك عند أول محاولة",
                    a = hashMapOf(Pair("نعم",Pair(0,false))),
                    b = hashMapOf(Pair("لا، هذا أمر صعب",Pair(3,false))),
                    c = hashMapOf(Pair("أحيانًا",Pair(3,false))),
                    d = hashMapOf(Pair(" لا يمكن لأحد القيام بذلك",Pair(3,true)))
            ))

            // 30
            add(Question("قد تغار من صديق لأنه",
                    a = hashMapOf(Pair("مبدع كل يوم",Pair(3,false))),
                    b = hashMapOf(Pair("اجتماعي وذو معارف كثير",Pair(3,false))),
                    c = hashMapOf(Pair("يصنع اختلافًا في حياة غيره",Pair(3,true))),
                    d = hashMapOf(Pair("لديه تحديات حياتية عظيمة",Pair(3,true)))
            ))
        }

        // Topic 02 Result

        var topic_two_result = ArrayList<String>().apply {


            add("تميل إلى الحساب والإطلاع على جميع خياراتك قبل اتخاذ قرار ما. تهمُّك الحقيقة كثيرًا وهذا لأن من أهم أهداف المنطق هو الوصول إلى الحقيقة، وغالبًا كلامك يطابق الواقع والحقائق ويتصف بالوضوح ولا تستخدم لغة مبهمة،  وتربط أفكارك بحقائق تعرفها." +
                    "تسأل نفسك أسئلة كثيرة ولديك شكوكٌ عظيمة، اهتمامك في التفاصيل حتى ولو كانت في مكان مألوف، يَصعب عليك الخروج عن جدولك اليومي." +
                    "تكره الأسئلة الغبية والأخطاء الواضحة وسرعان ما تصححها.\n" +
                    "في نهاية المطاف، أنت شخصٌ تستخدم رأسك أكثر من قلبك، وتفعل المستحيل لتحرس مشاعرك وتجعلها غير مرئية للعالم الخارجي، مهما حصل. \"رغم كل ما يحدث، يجب أن أبقى هادئًا مسترخيًا\" هذه إحدى أهم أفكار الناس من أمثالك، لجميعنا مشاعر، لكنك تخفي مشاعرك بشكل جيّد.")

            add("لكل الناس مشاعر، وطريقة تعاملك مع مشاعرك ودخول مشاعرك في تفاعلك مع الناس وفي حياتك اليوميّة تختلف بشكل كبير. في القرارات ذو التأثير الكبير، تحاول الجمع بين قلبك وعقلك راجيًا رضاك الداخلي." +
                    "لست مختلفًا عن غيرك، لكنّك تعلم متى يجب أن تُخفي مشاعرك ومتى يمكنك إظهارها، تنتبه لمعتقداتك وتلاحظ تغيّرها مع الزمن، قلبك وعقلك غالبًا في تناغم ويسهل عليك معظم قرارات الحياة." +
                    "تهتم للنقد، وتوصف بالهدوء نسبيًا، وتعرف أن لمشاعر الناس أهمية، تفضل أن تناقش وتظهر ما عندك قبل اتخاذ قرار جماعي." +
                    "يصعب عليك اتخاذ قرار بدون اعتبار التأثيرات الجانبية على من حولك، وتحب أن تجعل كل شيء متوازنًا، وسعادتك في سعادة الآخرين ومساعدتهم.")

            add("تكره التناقض مع أحدهم، تهتم وتشعر بغيرك في معظم الأوقات، تصنع قراراتك بناء على مبادئ وقواعد ثابتة عندك." +
                    "يعتبرك غيرك لطيفًا، وتشعر بالرضا عندما يتفق معك أحدهم، وإرضاء المقرّبين منك إحدى أولوياتك." +
                    "غالبًا، من السهل التفاهم معك لأنك صريح ولا تخفي أي شيء وبالنسبة لك فإن التعاون قبل التنافس.قرار يجعلك تشعر بالرضا أفضل من قرار نصحك به خبير، من السهل أن تشعر بما يشعر به غيرك، لديك قلب عظيم وتحب أن تعطي أكثر من أن تأخذ." +
                    "أحيانًا تواجه صعوبة باقناع نفسك بـالحقيقة المُــرّة، وذلك لأن مشاعرك ومشاعر غيرك تؤثر فيك.")

        }

        var topic_three =  ArrayList<Question>().apply{
            // 1
            add(Question(" أنت مشغول جدًا في أمر ما، يأتي صديقك ويكلمك عن قصّة في حياته الشخصية وبعض مشاكله",
                    a = hashMapOf(Pair("تقاطعه وتخبره أنك مشغول حاليًا",Pair(4,false))),
                    b = hashMapOf(Pair("تستمع إليه ولا تجرؤ على مقاطعته",Pair(4,true))),
                    c = hashMapOf(Pair("تعتقد أنه لا مشكلة إن استمعت إليه، وأن شغلك يمكن تأجيله",Pair(5,true))),
                    d = hashMapOf(Pair("تستمع إليه بإذن واحدة",Pair(5,false)))
            ))
            // 2
            add(Question("تعرّضك للأصوات الكثيرة والضجّة وحتى الإضاءة أحيانًا أمر مزعج",
                    a = hashMapOf(Pair("أرفض، ليست من صفاتي",Pair(5,true))),
                    b = hashMapOf(Pair("نوعًا ما",Pair(4,false))),
                    c = hashMapOf(Pair("لا أعلم",Pair(0,false))),
                    d = hashMapOf(Pair("أتفق",Pair(5,false)))
            ))
            // 3
           add(Question("ذهبتَ إلى طبيب الأسنان لمشكلةٍ في ضرسك، وتأخّر موعدك",
                    a = hashMapOf(Pair("تنظر إلى السّاعة كلّ دقيقة",Pair(4,false))),
                    b = hashMapOf(Pair("تغضب داخليًا وتنزعج، لكن تبقى هادئًا أمام النّاس",Pair(5,false))),
                    c = hashMapOf(Pair("تتكلّم مع النّاس هناك وتقول لهم أن هذا الطبيب دائمًا يتأخر",Pair(5,true))),
                    d = hashMapOf(Pair("تتذمّر بصوتٍ مرتفع",Pair(4,true)))
            ))
            // 4
            add(Question("تعمل أفضل ما عنْدك في بيئةٍ هادئة",
                    a = hashMapOf(Pair("أرفض",Pair(5,true))),
                    b = hashMapOf(Pair("نوعًا ما",Pair(4,false))),
                    c = hashMapOf(Pair("لا أعلم",Pair(0,false))),
                    d = hashMapOf(Pair("غالبًا",Pair(5,false)))
            ))
            // 5
            add(Question("لا تخاطر بالأمور ولا تتسرّع، بل تجري أبحاثًا قبل قراراتك",
                    a = hashMapOf(Pair("دائمًا",Pair(5,false))),
                    b = hashMapOf(Pair("لا أعلم",Pair(4,false))),
                    c = hashMapOf(Pair("نادرًا",Pair(4,true))),
                    d = hashMapOf(Pair("أحيانًا",Pair(0,false)))
            ))
            // 6
            add(Question("أنت في نقاش مع أحد زملائك عن مشروع تعملان عليه معًا",
                    a = hashMapOf(Pair("لا تحاول أن تناقض",Pair(5,false))),
                    b = hashMapOf(Pair("تعتقد أن ما يفعله صحيح",Pair(4,false))),
                    c = hashMapOf(Pair("تعطي رأيك وتدعم وجهة نظرك بالحقائق",Pair(5,true))),
                    d = hashMapOf(Pair("تقاطع زميلكَ بشكل مستمر",Pair(4,true)))
            ))
            // 7
            add(Question("أنت في رحلة إلى قلعة عجلون في الأردن",
                    a = hashMapOf(Pair("تبقى بعيدًا عن الجميع ولا تسمع ما يقال عن آثار القلعة",Pair(5,false))),
                    b = hashMapOf(Pair("تمشي متأخرًا بدون سؤال",Pair(4,false))),
                    c = hashMapOf(Pair("تكون مع معظم النّاس، وتنصت لما يقال",Pair(5,true))),
                    d = hashMapOf(Pair("تبقى في المقدمة وتحاول إضافة تعليق على كلّ ما يقال",Pair(4,true)))
            ))
            // 8
            add(Question("كـ طفل، وصفك النّاس بأنك هادئ",
                    a = hashMapOf(Pair("دائمًا",Pair(5,false))),
                    b = hashMapOf(Pair("لا أعلم",Pair(0,false))),
                    c = hashMapOf(Pair("نادرًا",Pair(4,true))),
                    d = hashMapOf(Pair("أحيانًا",Pair(0,false)))
            ))
            // 9
            add(Question("حتى ولو اسمتعتَ في رحلة، تشعر بالإرهاق النفسي بعدها",
                    a = hashMapOf(Pair("أبدًا، بل أشعر بالضّيق لأنها انتهت",Pair(5,true))),
                    b = hashMapOf(Pair("نوعًا ما",Pair(4,false))),
                    c = hashMapOf(Pair("نعم، هذه إحدى صفاتي",Pair(5,false))),
                    d = hashMapOf(Pair("أحيانًا",Pair(4,false)))
            ))
            // 10
            add(Question("خلال وجبة عشاء في مناسبةٍ، فإنك تواجه وقت عصيبًا مع النّاس الذين",
                    a = hashMapOf(Pair("يطلبون منكَ أن تخبر غيرهم بقصة حصلتْ معك",Pair(5,true))),
                    b = hashMapOf(Pair("يتكلمون مع بعضهم بشكلٍ خاص",Pair(5,true))),
                    c = hashMapOf(Pair("محادثتهم لا تكون إلا ضمن أمور تخصّهم هم فقط",Pair(4,true))),
                    d = hashMapOf(Pair("يتكلمون معك ولا يتركونك طوال المساء",Pair(4,true)))
            ))
            // 11
            add(Question("أخبرتَ أصدقائك نكتةً لكن لم يلاحظ أحدهم",
                    a = hashMapOf(Pair("تنسى وتعتقد أن هذا أفضل، فهي نكتة سخيفة أصلًا",Pair(4,false))),
                    b = hashMapOf(Pair("تخبرها لواحد منهم بشكل منفرد",Pair(4,true))),
                    c = hashMapOf(Pair("تخبر النكتة لعائلتك عند عودتك",Pair(4,true))),
                    d = hashMapOf(Pair("تكرر النكتة حتى يلاحظوا",Pair(5,true)))
            ))
            // 12
            add(Question("في يوم فراغك، تطلّع إلى",
                    a = hashMapOf(Pair("يوم هادئ وأن لا تزعجك مكالمة ما",Pair(4,false))),
                    b = hashMapOf(Pair("استغلال اليوم في الراحة والتخلص من القلق والتعب",Pair(4,false))),
                    c = hashMapOf(Pair("الاتصال بصديق وتلقي التحية وتسأل عن بعض الأمور",Pair(5,true))),
                    d = hashMapOf(Pair("سؤال أصدقائك والخروج للمتعة",Pair(4,true)))
            ))
            // 13
            add(Question("أن تكونَ وحيدًا أمرٌ ممل وكئيب",
                    a = hashMapOf(Pair("دائمًا",Pair(5,true))),
                    b = hashMapOf(Pair("لا",Pair(5,false))),
                    c = hashMapOf(Pair("نادرًا",Pair(0,false))),
                    d = hashMapOf(Pair("أحيانًا، حسب بعض الأشياء",Pair(4,false)))
            ))
            // 14
            add(Question("المحادثات القصيرة أفضل من المحادثات الطويلة ذات المعنى والتي تحتوي نقاشات",
                    a = hashMapOf(Pair("أحيانًا",Pair(4,true))),
                    b = hashMapOf(Pair("نعم أتفق",Pair(5,true))),
                    c = hashMapOf(Pair("لا أتفق، النقاشات أمر ممتع",Pair(0,false))),
                    d = hashMapOf(Pair("لا أعلم",Pair(4,true)))
            ))
            // 15
            add(Question("تفضل أن يكون شريك حياتك",
                    a = hashMapOf(Pair("كثير الكلام والحركة والمتعة",Pair(5,true))),
                    b = hashMapOf(Pair("هادئ وله مواقف ممتعة",Pair(4,true))),
                    c = hashMapOf(Pair("كثير الصمت ولا يتكلم إلا عند الضرورة",Pair(5,false))),
                    d = hashMapOf(Pair("لا أفضل أيّ شريك حياة",Pair(0,false)))
            ))
            // 16
            add(Question("تشعر أن المنافسة أمر ممتع وحماسي",
                    a = hashMapOf(Pair("نعم، إلى حد ما",Pair(4,true))),
                    b = hashMapOf(Pair("غالبًا",Pair(4,true))),
                    c = hashMapOf(Pair("أبدًا",Pair(5,true))),
                    d = hashMapOf(Pair("حسب نوع المنافسة",Pair(4,false)))
            ))
            // 17
            add(Question("تتذكر أصدقاء كثر في طفولتك، وأنك كنت كثير الحركة والكلام",
                    a = hashMapOf(Pair("طبعًا",Pair(5,true))),
                    b = hashMapOf(Pair("لا، لم يكن عندي الكثير من الأصدقاء",Pair(4,false))),
                    c = hashMapOf(Pair("نعم، لكني لم أكن كثير الكلام",Pair(4,false))),
                    d = hashMapOf(Pair("لا أتذكر",Pair(0,false)))
            ))
            // 18
            add(Question("في نفس وليمة العشاء، ينتقل الحديث إلى موضوع لا تعرف عنه شيئًا",
                    a = hashMapOf(Pair("لا تجرؤ أن تظهر أنك لا تعرف عن الموضوع أي شي",Pair(4,true))),
                    b = hashMapOf(Pair("تتابع الحديث بملل",Pair(4,false))),
                    c = hashMapOf(Pair("تسأل بعض الأسئلة وتعرف ما تحتاجه",Pair(4,false))),
                    d = hashMapOf(Pair("تحاول تغيير الحديث",Pair(4,true)))
            ))
            // 19
            add(Question("في نفس الوليمة مرة ثالثة، هناك شخص خجول وهادئ",
                    a = hashMapOf(Pair("تلاحظ أنه يجلس لوحده وتذهب وتبدأ حديثًا معه",Pair(4,true))),
                    b = hashMapOf(Pair("تلاحظه لكن لا تذهب إليه",Pair(4,false))),
                    c = hashMapOf(Pair("لا تلاحظه أبدًا",Pair(5,true))),
                    d = hashMapOf(Pair("تلاحظه وتبتسم في وجهه دون أن تقترب",Pair(4,false)))
            ))
            // 20
            add(Question("في عملك، شخص يطلب المساعدة للمرة الخمسين",
                    a = hashMapOf(Pair("تساعده، كالعادة. فالنّاس بحاجة للمساعدة",Pair(5,true))),
                    b = hashMapOf(Pair("تساعده، لأنك مفيد دائمًا",Pair(5,false))),
                    c = hashMapOf(Pair("تطلب منه أن يبحث عن غيرك بطريقة لطيفة",Pair(4,false))),
                    d = hashMapOf(Pair("تتكلم بصوت مرتفع و تظهر انزعاجك",Pair(4,true)))
            ))
            // 21
            add(Question("شاهدت فلمًا مع أصدقائك وعند نهايته كانت الآراء مختلفة",
                    a = hashMapOf(Pair("بجرأة وحماس تظهر رأيك",Pair(4,true))),
                    b = hashMapOf(Pair("تحاول اقناع غيرك برأيك",Pair(4,true))),
                    c = hashMapOf(Pair("تُبقي رأيك لنفسك وتقول ما عندك إن سئلت عنه",Pair(4,false))),
                    d = hashMapOf(Pair("لا تبدي أي رأي",Pair(4,false)))
            ))
            // 22
            add(Question("تأخر أحدهم عن اجتماع ما",
                    a = hashMapOf(Pair("تصطنع مشكلة أمام الجميع",Pair(4,true))),
                    b = hashMapOf(Pair("تخبره أنه لا مشكلة، حتى وإن كنت تعتقد غير ذلك",Pair(4,false))),
                    c = hashMapOf(Pair("تخبره أن تأخر وكان يجب أن يحرص على الوقت بشكل أكبر",Pair(5,true))),
                    d = hashMapOf(Pair("تنظر إليه نظرة استغراب دون أن تكلمه",Pair(4,false)))
            ))
            // 23
            add(Question("لم تجد هاتفك في جيبك",
                    a = hashMapOf(Pair("تبحث بشكل سري في البداية ولا تخبر أحدًا",Pair(5,false))),
                    b = hashMapOf(Pair("تتهم من حولك لأنه ربما أحدهم قد وضعه في مكان ما",Pair(4,true))),
                    c = hashMapOf(Pair("تغضب وتنزعج ولا تخبر غيرك لمَ أنت منزعج",Pair(4,true))),
                    d = hashMapOf(Pair("تبحث وتسأل من حولك وتتجنب معرفة الكثير من النّاس بالأمر",Pair(4,false)))
            ))
            // 24
            add(Question("تعتبر نفسك كثير الكلام",
                    a = hashMapOf(Pair("نعم، إلى حد ما",Pair(4,true))),
                    b = hashMapOf(Pair("غالبًا",Pair(4,true))),
                    c = hashMapOf(Pair("أبدًا",Pair(0,false))),
                    d = hashMapOf(Pair("حسب من هم أمامي",Pair(4,false)))
            ))
            // 25
            add(Question("تقفز من فعل أمر ما إلى شيء آخر ولا تركز على أمر واحد",
                    a = hashMapOf(Pair("دائمًا",Pair(4,false))),
                    b = hashMapOf(Pair("أحيانًا",Pair(0,false))),
                    c = hashMapOf(Pair("لا، أركز على أمر واحد وأنهيه ثم أفكر ببدء مسألة جديد",Pair(4,true))),
                    d = hashMapOf(Pair("حسب الأمور وما يستجد",Pair(4,false)))
            ))
        }

        // Topic 03 Results ->

        var topic_three_result = ArrayList<String>().apply{

            // If trav >= trov and trav >= trov +20
            add("مَصدر مسرّتك وسعادتك بشكل عام هو العالم الخارجي وتفاعلك معه. تولّد الحماس حيثما كنت، كثير الكلام ومن السّهل بالنسبة لك التعرف على أشخاصٍ جدد.\n" +
                    "تملك ابتسامة رحبةً وتعطيها لمعظم البشر حولك. عند العمل الجماعي غالبًا ما تودُّ إظهار لمستك الشخصيّة وما عندك من معلومات.\n" +
                    "معظم الوقت فإنك تتحدث بما يخطر في بالك، فهذه طبيعتك، \n" +
                    "تكره الوحدة والعزلة، لديك العديد من المعارف والأصدقاء.\n" +
                    "من السهل لك أن تقود مسيرة أو أن تكون مركز الحديث ولا تمانع في ذلك، وتعتقد بأنك قادر على منافسة أي عقبة تواجهك، فإن أعطتك الحياة ليمونًا صنعتَ منه عصيرًا")

            // If trav > trov and trav < trov + 10
            add("سعيُكَ للحصول على التّوازن والسّلام الدّاخلي يكون من خلال تفاعلك مع من حولك، وبقدرتك على استيعاب المواقف الاجتماعيّة بدرجة جيّدة.\n" +
                    "يمكن القول أنك شخص بين الفئتين، أي لديك صفات عجيبة ومتشابكة، فمثلًا قد تبدو هادئًا أمام الغرباء، وكثير الحركة والكلام أمام عائلتك وأصدقائك.\n" +
                    "قد تعجبك الحفلات والمناسبات الإجتماعيّة ضمن أجواء معيّنة، لكنّك تحتاج وقتًا لتعيد ضبط أفكارك من جديد ولا تمانع إن قضيتَ بعض الوقت وحيدًا.\n" +
                    "أنت مرن في تواصلك مع غيرك، وبهذا فإن مشاكلك الإجتماعية نادرة.\n" +
                    "معرفتك متى يجب أن تتكلم ومتى يجب أن تسكت تعتبر من أفضل صفات الحكمة الإجتماعيّة")

            // If trov > trav and trov >= trav +20
            add("أصدقاؤكَ قليلون ومعارفكَ محدودة نسبيًا، تعجبك المحادثات والمناسبات بعدد أشخاص قليل، وخصوصًا مع من تعرفهم معرفة جيّدة. تحب الخصوصيّة وقلّ ما تدع باب مكتبك أو غرفتك مفتوحًا.\n" +
                    "الإستقلاليّة هي إحدى أولوياتك، حيث تبتعد ويقل تفاعلك مع الغرباء.\n" +
                    "غالبًا تتعلّم بالمشاهدة وتراقب بصمت ولا تسأل الكثير من الأسئلة.\n" +
                    "فهم نفسك ووعيك الداخلي أمر مهم بالنسبة لك، فلطالما قضيت أوقاتًا مع نفسك بحثًا عما يحرك داخلك ويفسّر ذاتك.\n" +
                    " عمومًا، أنت هادئ ويصعب الحصول على ثقتك وأحيانًا يظنّك البعض خجولًا وأنت قد لا تكون، فالخجل أمر، والاستقلاليّة أمر آخر.\n" +
                    "لا تتكلم إلا إذا احتاج الأمر، حتى وإن كان عندك معلومة تضيفها على النقاش فإنه يصعب عليك قولها دون أن يُطلب منك.\n" +
                    "لا يهمك انتباه الغرباء ولا تحب أن تكون مركز الحديث في معظم الوقت")
        }

        // Topic 04

        var topic_four = ArrayList<Question>().apply{

            //01
            add(Question("تعود من عملك وتكون فخورًا جدًا لتخبر عائلتك أنك",
                    a = hashMapOf(Pair("أنهيتَ قضية صعبة",Pair(3,false))),
                    b = hashMapOf(Pair("أتممتَ عقدًا مهمًا",Pair(3,true))),
                    c = hashMapOf(Pair("أنقذتَ أحدهم",Pair(3,true))),
                    d = hashMapOf(Pair("نشرتَ كتابًا",Pair(3,false)))
            ))

            //02
            add(Question("تقنيًا، أي مرآة تشتريها، هي مستعملة وليست جديدة 100%",
                    a = hashMapOf(Pair("تقنيًا، هذا التطبيق مستعمل",Pair(4,false))),
                    b = hashMapOf(Pair("تعتمد على نوع المرآة",Pair(3,false))),
                    c = hashMapOf(Pair("لا، جميع المرايا جديدة",Pair(3,true))),
                    d = hashMapOf(Pair("إلا إذا كانت مغلّفة",Pair(4,true)))
            ))

            //03
            add(Question("إذا كان عندك خيارين، وأُخِذ منكَ واحدٌ فعليًّا ليس لديك أي خيار الآن",
                    a = hashMapOf(Pair("لا زال لديك خيار عدم قبول أي خيار",Pair(4,false))),
                    b = hashMapOf(Pair("نعم ليس لديك إلا خيار واحد",Pair(3,true))),
                    c = hashMapOf(Pair("نعم، أنت تفقد خيارين إذا كان عندك خياران وخسرت واحدً",Pair(3,true))),
                    d = hashMapOf(Pair("لم أفهم السؤال",Pair(0,false)))
            ))

            //04
            add(Question("بعض الأطفال في الأجيال الحاليّة إما حسّاس لكل شيء أي مفرط في الحساسيّة أو عديم الإحساس،كم نسبة اتفاقك مع هذه العبارة",
                    a = hashMapOf(Pair("0%",Pair(3,false))),
                    b = hashMapOf(Pair("10% - 30%",Pair(3,false))),
                    c = hashMapOf(Pair("40% - 80%",Pair(4,true))),
                    d = hashMapOf(Pair("100%",Pair(4,true)))
            ))

            //05
            add(Question("كل شيء سيكون على مايرام ما نسبة اتفاقك مع هذه العبارة",
                    a = hashMapOf(Pair("0%",Pair(4,false))),
                    b = hashMapOf(Pair("10% - 30%",Pair(3,true))),
                    c = hashMapOf(Pair("40% - 80%",Pair(3,true))),
                    d = hashMapOf(Pair("100%",Pair(3,true)))
            ))

            //06
            add(Question("عندما يصطدم اصبع قدمك بكرسي أو ماشابه،بالنسبة لذلك الشيء فإنك ركلته دون إذنه",
                    a = hashMapOf(Pair("بالنسبة لي فإنه يقف في طريقي",Pair(3,false))),
                    b = hashMapOf(Pair("نعم هذا صحيح",Pair(3,true))),
                    c = hashMapOf(Pair("بالنسبة لي فإن ذلك الشيء شرير",Pair(3,false))),
                    d = hashMapOf(Pair("عندما أصطدمُ بشيء، فأنا أعتذر",Pair(3,true)))
            ))

            //07
            add(Question("إذا مررتَ بأشخاص وضحكوا عند مرورك، فإن احتمال فرضَك أنهم يضحكون ويسخرون منك هو",
                    a = hashMapOf(Pair("0%",Pair(3,false))),
                    b = hashMapOf(Pair("10% - 40%",Pair(3,true))),
                    c = hashMapOf(Pair("50% - 85%",Pair(3,true))),
                    d = hashMapOf(Pair("100%",Pair(3,true)))
            ))

            //08
            add(Question("الإكتئاب هو",
                    a = hashMapOf(Pair("حالة من التعاسة واليأس",Pair(3,false))),
                    b = hashMapOf(Pair("حالة من عدم القدرة على الشعور بأي شيء",Pair(3,false))),
                    c = hashMapOf(Pair("حالة من عدم القدرة على الشعور بالسعادة",Pair(3,false))),
                    d = hashMapOf(Pair("وضع توفير الطاقة",Pair(3,true)))
            ))

            //09
            add(Question("إذا كنت أسرع من أسرع شخص في العالم، فهل يمكنك أن تكون أسرع من أسرع شخص في العالم؟",
                    a = hashMapOf(Pair("لا",Pair(3,false))),
                    b = hashMapOf(Pair("نعم، طبعًا",Pair(4,true))),
                    c = hashMapOf(Pair("لا أعلم",Pair(0,false))),
                    d = hashMapOf(Pair("حسب سرعتيْ عندما أكون أسرع شخص في العالم",Pair(3,false)))
            ))

            //10
            add(Question("إذا قفزتَ من طائرة وكان معك مظلّة وتعطّلت على ارتفاع 1200 متر، وكانت سرعتك 90 مترًا في الثانية فإنه معك وقتًا لإصلاحها مدت",
                    a = hashMapOf(Pair("حسب الارتفاع الذي سأسقطُ منه",Pair(3,false))),
                    b = hashMapOf(Pair("بضع ثوان",Pair(3,false))),
                    c = hashMapOf(Pair("دقائق أو أقل",Pair(3,false))),
                    d = hashMapOf(Pair("بقية حياتي",Pair(3,true)))
            ))

            //11
            add(Question("تعتبر مكان لسانك مناسبًا، وموضعه متناسب مع أسنانك",
                    a = hashMapOf(Pair("نعم، تمامًا",Pair(4,true))),
                    b = hashMapOf(Pair("لا، يضايقني طبعًا",Pair(3,false))),
                    c = hashMapOf(Pair("ولا يمكن تجاهل أنفك إراديًا",Pair(4,true))),
                    d = hashMapOf(Pair("حسب حالتي الفكرية والجسمية",Pair(3,false)))
            ))

            //12
            add(Question("تنظر إلى الساعة في اليوم",
                    a = hashMapOf(Pair("مرتان أو أقل",Pair(3,false))),
                    b = hashMapOf(Pair("لا أعلم",Pair(0,false))),
                    c = hashMapOf(Pair("لا أنظر إلا الساعة أبدً",Pair(3,true))),
                    d = hashMapOf(Pair("كثيرًا",Pair(3,false)))
            ))

            //13
            add(Question("البرمجة وتحليل البيانات هو أقرب علم إلى السحر والشعوذة",
                    a = hashMapOf(Pair("نعم، فعلًا",Pair(3,true))),
                    b = hashMapOf(Pair("لا، كلاهما مختلفان",Pair(3,false))),
                    c = hashMapOf(Pair("حسب ما تبرمجهًُ",Pair(3,false))),
                    d = hashMapOf(Pair("البرمجة في غرفة مظلمة مع قليل من الأنوار، نعم هذا سحر",Pair(3,true)))
            ))

            //14
            add(Question("شخصٌ يشتري سيّارة من أجل خصائص السلامة فيها، هو شخص",
                    a = hashMapOf(Pair("يعترف أنه شخص فاشل في قيادة السيارات",Pair(3,false))),
                    b = hashMapOf(Pair("يعرف أن بعض السائقين خطرين",Pair(3,false))),
                    c = hashMapOf(Pair("لديه أطفالًُ",Pair(3,false))),
                    d = hashMapOf(Pair("حسب خصائص السلامة",Pair(3,true)))
            ))

            //15
            add(Question("إذا أردتَ معرفة شخص أكثر، اجعله يتصل بشبكة إنترنت بطيئة جدًا ما نسبة اتفاقك ؟",
                    a = hashMapOf(Pair("0%",Pair(3,false))),
                    b = hashMapOf(Pair("10% - 40%",Pair(3,false))),
                    c = hashMapOf(Pair("50% - 85%",Pair(3,false))),
                    d = hashMapOf(Pair("100%",Pair(4,true)))
            ))

            //16
            add(Question("سبب اختراع البشر للعملة والمال هو",
                    a = hashMapOf(Pair("طمع البشر",Pair(3,true))),
                    b = hashMapOf(Pair("قلة تعاونهم",Pair(3,true))),
                    c = hashMapOf(Pair("لا، المال هو أداة تحويل، أنت تبدل ما لا تحتاجه بشيء تحتاجه",Pair(3,false))),
                    d = hashMapOf(Pair("لا، فقط لا",Pair(3,false)))
            ))

            //17
            add(Question("ضوء القمر هو ضوء الشمس لكنه مستعمل",
                    a = hashMapOf(Pair("نعم، علميًا صحيح",Pair(3,false))),
                    b = hashMapOf(Pair("ضوء القمر هو فعليًا ضوء الشمس",Pair(3,false))),
                    c = hashMapOf(Pair("لا أعلم كثيرًا في أمور الفضاء",Pair(0,false))),
                    d = hashMapOf(Pair("ضوء المصباح هو ضوء مستعمل من قبل شركات الكهرباء",Pair(3,true)))
            ))

            //18
            add(Question("تعداد السكان الحالي، في 2018 هو 7.5 مليار عاش على الأرض 115 مليارمنذ وجود البشر، احتمال بقائك على قيد الحياة  والخلود المؤبد هو 7%",
                    a = hashMapOf(Pair("هذا هراء",Pair(3,false))),
                    b = hashMapOf(Pair("استخدمت أرقامًا وحسابات كثيرة، هذا صحيح",Pair(4,true))),
                    c = hashMapOf(Pair("يبدو أن من كتب السؤال لم ينجح في الرياضيات",Pair(3,false))),
                    d = hashMapOf(Pair("نعم، هكذا المنطق",Pair(4,true)))
            ))

            //19
            add(Question("إذا ساعدَ صديقك شخصًا، ولم يعلم الناس أن صديقك هو من ساعده، ولم يخبرهم صديقُك، أول اعتقاد لك",
                    a = hashMapOf(Pair("أنّ صديقك يحاول أن يكون لطيفًا أمام البعض",Pair(3,false))),
                    b = hashMapOf(Pair("أنّ صديقك حقًا لطيف",Pair(3,false))),
                    c = hashMapOf(Pair("أنّه يجب على الناس المعرفة بمساعدة صديقك للشخص",Pair(3,true))),
                    d = hashMapOf(Pair("أنّه يجب على من تمت مساعدته ردُّ المعروف",Pair(3,true)))
            ))

            //20
            add(Question("إذا كانت الأرض مستوية فإن أكبر خوف عند البشرية",
                    a = hashMapOf(Pair("أن تضرب النيازك الأرض من الأسفل",Pair(3,true))),
                    b = hashMapOf(Pair("تضرب النيازك طرف الأرض وتسبب انقلابًا للأرض",Pair(3,true))),
                    c = hashMapOf(Pair("أن نموت بسبب دوان الارض وحركتها العجيبة",Pair(3,false))),
                    d = hashMapOf(Pair("لا يوجد قطب جنوبي ولا شمالي للأرض، مما يسبب موت الجميع",Pair(3,false)))
            ))

            //21
            add(Question("الجميع يحب المماطلة ويكره المماطلة ما نسبة اتفاقك؟",
                    a = hashMapOf(Pair("0%",Pair(4,false))),
                    b = hashMapOf(Pair("10% - 40%",Pair(3,true))),
                    c = hashMapOf(Pair("50% - 85%",Pair(3,true))),
                    d = hashMapOf(Pair("100%",Pair(3,true)))
            ))

            //22
            add(Question("عندما تعطي أحدهم هديّة",
                    a = hashMapOf(Pair("فإنك تندم على ذلك بعد فترة",Pair(3,false))),
                    b = hashMapOf(Pair("تظن أن الهدية هي إعلان وتذكار لك",Pair(4,true))),
                    c = hashMapOf(Pair("لا تعطي الهدايا",Pair(4,true))),
                    d = hashMapOf(Pair("تظن أن الهدية فكرة جيّدة لجعلهم يفكرون فيك",Pair(3,false)))
            ))

            //23
            add(Question("تعتقد أنه شخص ما، في مكان ما، في وقت ما قد التقط صورة لك بدون علمك",
                    a = hashMapOf(Pair("نعم، ربما",Pair(3,true))),
                    b = hashMapOf(Pair("لا أكترث لذلك",Pair(0,false))),
                    c = hashMapOf(Pair("لا أحد يصوّر أحدًا بلا سبب",Pair(3,false))),
                    d = hashMapOf(Pair("أنا أترُكهم يفعلون ذلك وألاحِظهم",Pair(3,true)))
            ))

            //24
            add(Question("نحن مجرد عقول محبوسة في أجسام بشرية لم نختر الدخول ولا الوجود",
                    a = hashMapOf(Pair("لم نختر الوجود لكن بإمكاننا اختيار السعادة",Pair(4,true))),
                    b = hashMapOf(Pair("الجسم هو أداة للعقل وليس حبسًا",Pair(3,false))),
                    c = hashMapOf(Pair("للعقل أن يفكر كما يشاء",Pair(3,false))),
                    d = hashMapOf(Pair("العقل والجسم جزءان متكاملان",Pair(3,false)))
            ))

            //25
            add(Question("من أجل أن تفكر خارج الصندوق، يجب أن تفكر داخل الصندوق أولًا، ما نسبة اتفاقك؟",
                    a = hashMapOf(Pair("0%",Pair(3,true))),
                    b = hashMapOf(Pair("10% - 40%",Pair(0,false))),
                    c = hashMapOf(Pair("50% - 85%",Pair(4,false))),
                    d = hashMapOf(Pair("100%",Pair(4,false)))
            ))
        }

        var topic_four_result = java.util.ArrayList<String>().apply {
            add("أنت شخص شديد الملاحظة، ويصعب عليك مخالفة القوانين، تحب مراقبة من حولك وتدع الفرضيّات لأصحبها. تميل الى العيش في الحاضر والتركيز في احداثه ولا تكترث لأسباب حدوث شيء ما أو عدم حدوثه." +
                    "و أيضًا، من السهل أن تتعامل مع الحقائق والأدوات في حياتك اليوميّة، ومن أهم صفاتك القدرة على التركيز على شيء واحد حتى النهاية." +
                    "ﺗﺮﻛﺰ ﻋﻠﻰ اﻟﻮﺿﻮح واﻟﺪﻗﺔ ﻓﻲ اﻟﺤﺪﯾﺚ واﻟﻨﻘﺎش، ومن الصعب تغيير مبادئك، واحيانًا قد تشعر بأن غيرك سخيف أو يفكر بلا منطق، وهذا طبيعي لأن الطرف الآخر قد يراك بسيط و واقعي جدًا")

            add("من السهل لكً أن تصدق المعلومات بدون دليل، ليس سذاجه بل شعور داخلي يدفعك لذلك." +
                    "يصعب عليك مواجة النغيير، لكنك أحيانًا تحارب من أجله تغيّر مبادئك حسب المستجدات و معطيات الأمور، تؤمن أنه لكل قاعدة استثناء." +
                    "ﺛﻘﺘﻚ ﺑﻨﻔﺴﻚ ﻣﻦ أﻋﻈﻢ ﺻﻔﺎﺗﻚ، ﯾﻤﻜﻨﻚ اﺳﺘﺨﺪام ﻃﺎﻗﺘﻚ ﻛﯿﻔﻤﺎ ﺗﺸﺎء في معظم الوقت ولا مانع إن وضعتَ جدولًا ومشيت فيه." +
                    "عيناك أحيانًا مثل عيني النسر، لا تترك أي تفاصيل دون مراجعتها، واحيانًا فأنك تتجاهل التفاصيل لأنك لا تريد أن تتكلم أو تناقش أحدًا فيها." +
                    "يمكن القول ايضًا، أنك تستخدم المنطق و الخيال بالرغم أنهما متناقضان، في تحليل المعلومات و ما تستوعبه من العالم، بالنسبة لك المتسقبل امر مهم جدًا، لكنك الآن في الحاضر و تعيشه لحظة بلحظة.")

            add("في معظم الأحيان، تكون خياليًا ويمكنك التفكير خارج الصندوق، التغيير والإبداع إحدى مهاراتك وهو بالنسبة لك أهم من الثبات وعدم التجديد، وغالبًا ما تركّز على المعاني المخفية خلف ما يحدث." +
                    "تعتمد على مخيلتك وأفكارك والاحتمالات التي تضعها للعيش، وكثيرًا ما تسأل نفسك وتتساءل لِمَ تحدث الأحداث بهذه الطريقة، ولِمَ لم تحدث بطريقة أخرى، تشعر بأنك لست من هذا العالم، يمكن أن تجلس أو تراقب غيرك لكن عقلك أحيانًا يغيب ليحضر لك تساؤلات عجيبة من الحاضر والماضي والمستقبل." +
                    "مع كل هذا، فأنت شخص تستقبل النقد والاقتراح بعقل متفتّح، وتؤمن بأنك تستطيع أن تطوّر من ذاتك لأبعد الحدود.")
        }
    }
}