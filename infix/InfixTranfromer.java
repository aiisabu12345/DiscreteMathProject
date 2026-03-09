import java.util.Stack;

public class InfixTranfromer {
    public String findInToPost(String infix){                                                   // method การหา Postfix โดยรับ infix มาแปลง
        Stack<Character> op = new Stack<>();                                                    // Stack op มีหน้าที่เก็บ operator                     
        Stack<String> post = new Stack<>();                                                     // Stack post มีหน้าที่เก็บตัวเลข และ postfix 
        int i = 0;                                                                              

        while(i< infix.length()){                                                               // i จะเพิ่มไปเรื่อยๆจนกว่าจะครบสมการ infix
            if(infix.charAt(i) >= '0' && infix.charAt(i) <= '9'){                               // เงื่อนไขตรวจสอบตัวเลขหรือเครื่องหมาย
                String tem = "";                                                                // tem นำ character เลขแต่ละหลักมารวมเป็น string
                while(i< infix.length() && infix.charAt(i) >= '0'                               // เงื่อนไขตรวจสอบหลักที่เป็นเลข
                        && infix.charAt(i) <= '9'){
                    tem += infix.charAt(i);
                    i++;                                                                        
                }
                System.out.println("push " + tem + " into post : " + post);
                post.push(tem);                                                                 // post เก็บ tem
            }
            else{
                if(infix.charAt(i) == ')'){                                                     // เงื่อนไชดัก ) 
                    while(!op.isEmpty()&&op.peek() != '('){                                     // เงื่อนไขเก็บ operator จนกว่าจะเจอ (                      
                        System.out.println("pull " + op.peek() + " from op : " + op 
                        + " push into post : " + post);
                        post.push(String.valueOf(op.pop()));                                    
                    }
                    if(!op.isEmpty())op.pop();                                                  //เงื่อนไขให้ op pop อีกหากยังมีอยู่
                    i++;
                }
                else if(!op.isEmpty() && getScore(op.peek()) < 4 
                        && getScore(infix.charAt(i)) < 4 
                        && getScore(infix.charAt(i)) <= getScore(op.peek()))                    //เงื่อนไขตรวจสอบหากลำดับความสำคัญของสัญลักษณ์น้อยกว่าทำการดึงตัวก่อนหน้าของ
                {                                                                               //op มาใส่ใน post
                    System.out.println("pull " + op.peek() + " from op : " + op 
                    + " push into post : " + post);
                    post.push(String.valueOf(op.pop()));
                }
                else{
                    System.out.println("push " + infix.charAt(i) + " into op : " +  op);
                    op.push(infix.charAt(i));                                                   //op เก็บ operator
                    i++;
                }
            }
        }


        while(!op.isEmpty()){                                                                   //เงื่อนไขลูปเมื่อ infix.length จบก่อนแต่ยังเหลือสมาชิกใน op 
            char cur = op.pop();
            if(getScore(cur) < 4){                                                              //เงื่อนไขเมื่อ score น้อยกว่า 4 ซึ่งเป็น ^ * / + -
                System.out.println("pull " + cur + " from op : " + op +                         //และทำการเก็บใส่ใน post
                 " push into post : " + post);                                                  
                post.push(String.valueOf(cur));                                                 
            }
        }

        String out = "";                                                                        // out แปลง post stack ให้กลายเป็น String
        while(!post.isEmpty()){
            out = " " + post.pop() + out;
        }
        return out;
    }

    public String findInToPre(String infix){                                                    // method การหา Prefix โดยรับ infix มาแปลง 
        infix = reverse(infix);                                                                 // reverse infix ให้เรียงลำดับใหม่จากท้ายไปหน้า
        Stack<Character> op = new Stack<>();                                                    // Stack op มีหน้าที่เก็บ operator
        Stack<String> post = new Stack<>();                                                     // Stack post มีหน้าที่เก็บตัวเลข และ prefix
        int i = 0;

        while(i< infix.length()){                                                               // i จะเพิ่มไปเรื่อยๆจนกว่าจะครบสมการ infix
            if(infix.charAt(i) >= '0' && infix.charAt(i) <= '9'){                               // เงื่อนไขตรวจสอบตัวเลขหรือเครื่องหมาย
                String tem = "";                                                                // tem นำ character เลขแต่ละหลักมารวมเป็น string
                while(i< infix.length() && infix.charAt(i) >= '0' && infix.charAt(i) <= '9'){   // เงื่อนไขตรวจสอบหลักที่เป็นเลข
                    tem += infix.charAt(i);
                    i++;
                }
                System.out.println("push " + tem + " into post : " + post);
                post.push(tem);                                                                 // post เก็บ tem
            }
            else{
                if(infix.charAt(i) == ')'){                                                     // เงื่อนไชดัก )
                    while(!op.isEmpty()&&op.peek() != '('){                                     // เงื่อนไขเก็บ operator จนกว่าจะเจอ (
                        System.out.println("pull " + op.peek() + " from op : " 
                        + op + " push into post : " + post);
                        post.push(String.valueOf(op.pop()));                                    
                    }
                    if(!op.isEmpty())op.pop();                                                  //เงื่อนไขให้ op pop อีกหากยังมีอยู่
                    i++;
                }
                else if(!op.isEmpty() && getScore(op.peek()) < 4 &&getScore(infix.charAt(i)) < 4//เงื่อนไขตรวจสอบหากลำดับความสำคัญของสัญลักษณ์น้อยกว่าทำการดึงตัวก่อนหน้าของ 
                && getScore(infix.charAt(i)) < getScore(op.peek())){                            //op มาใส่ใน post
                    System.out.println("pull " + op.peek() + " from op : " 
                    + op + " push into post : " + post);
                    post.push(String.valueOf(op.pop()));
                }
                else{
                    System.out.println("push " + infix.charAt(i) + " into op : " + op);
                    op.push(infix.charAt(i));                                                   //op เก็บ operator
                    i++;
                }
            }
        }


        while(!op.isEmpty()){                                                                   //เงื่อนไขลูปเมื่อ infix.length จบก่อนแต่ยังเหลือสมาชิกใน op
            char cur = op.pop();
            if(getScore(cur) < 4){                                                              //เงื่อนไขเมื่อ score น้อยกว่า 4 ซึ่งเป็น ^ * / + -
                System.out.println("pull " + cur + " from op : "                                //และทำการเก็บใส่ใน post
                + op + " push into post : " + post);
                post.push(String.valueOf(cur));                                                 
            }
        }

        String out = "";                                                                        // out แปลง post stack ให้กลายเป็น String
        while(!post.isEmpty()){                                             
            out = " " + post.pop()+ out;
        }
        return reverse(out).trim();                                                             // out ถูกคืนค่าแบบ reverse 
    }
    
    public int callLikeAlu(String s){                                                           // method เรียกใช้คำนวณ Alu
        System.out.println("Infix = "+s);
        String postfix = findInToPost(s);                                                       // postfix เรียก findInToPost เพื่อแปลง infix เป็น postfix
        System.out.println("Postfix = "+postfix);

        String[] splitPost = postfix.split(" ");                                                // splitPost แยก postfix 
        Stack<Integer> stack = new Stack<>();                                                   // stack ใช้ในการเก็บ postfix ที่ใส่เข้ามา
        System.out.println("\n======================\nProcess:");
        for(String sp : splitPost){                                                             // splitPost เรียก element ในตัวเองมาทีละตัว
            if(sp.length()>0&&sp.charAt(0) >= '0'&&sp.charAt(0)<='9'){                          // เงื่อนไขตรวจสอบการเป็นตัวเลข
                System.out.println("Push: "+sp+" in stack");
                stack.push(Integer.parseInt(sp));                                               // stack ทำการเก็บตัวเลข 
            }
            else if(stack.size() > 1){                                                          // เงื่อนไข stack ใหญ่กว่า 1 จะทำการ pop ออกมาเลย
                int second = stack.pop();                                                       // second ดึงตัวช้างบนสุด
                int first = stack.pop();                                                        // first ดึงตัวรองลงบนสุด
                System.out.printf("Pop %d out of stack\n",second);
                System.out.printf("Pop %d out of stack\n",first);
                int out = 0;                                                                    // out กำหนดผลรวม
                switch (sp) {                                                                   // เงื่อนไข sp ตามกรณี + - * / ^
                    case "+" -> out = first + second;                                           
                    case "-" -> out = first - second;
                    case "*" -> out = first * second;
                    case "/" -> out = first / second;
                    case "^" -> out = (int)Math.pow(first, second);                             
                }
                System.out.printf("Calculate: %d %s %d = %d\n",first,sp,second,out);
                System.out.printf("Push: %d in stack\n",out);
                stack.push(out);                                                                // stack เก็บ out
            }
        }
        int ans = stack.pop();                                                                  // stack ดึง ผล alu
        System.out.printf("Pop %d out of stack to be answer\n======================\n\n",ans);
        return ans;                                                                             // ans คืนค่ากลับไป
    }

    public String reverse(String s){                                                            // method การย้อนกลับตัวอักษรใน String ทั้งหมด
        String newS = "";                                                                         
        char cur;                                                                               
        for(int i = s.length() - 1;i>=0;i--){
            cur = s.charAt(i);
            if(cur == ')')
                cur = '(';
            else if(cur == '(')
                cur = ')';
            newS = newS + cur;
        }
        return  newS;
    }

    public int getScore(char op){                                                                // method ตรวจสอบ op เพื่อส่ง int ที่เป็นลำดับความสำคัญกลับ
        switch (op) {                                                                            // ให้ลำดับความสำคัญจากมากไปน้อย () ^ * / + -
            case '(':                      
            case ')':
                return 4;
            case '^':
                return 3;
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
        }
        return -1;
    }

}