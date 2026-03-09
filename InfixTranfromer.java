import java.util.Stack;

public class InfixTranfromer {
    public String findInToPost(String infix){
        Stack<Character> op = new Stack<>();                                                    // Stack op มีหน้าที่เก็บ operator                     
        Stack<String> post = new Stack<>();                                                     // Stack post มีหน้าที่เก็บตัวเลข และ postfix 
        int i = 0;                                                                              

        while(i< infix.length()){                                                               // i จะเพิ่มไปเรื่อยๆจนกว่าจะครบสมการ infix
            if(infix.charAt(i) >= '0' && infix.charAt(i) <= '9'){                               // เงื่อนไขตรวจสอบตัวเลขหรือเครื่องหมาย
                String tem = "";                                                                // tem นำ character เลขแต่ละหลักมารวมเป็น string
                while(i< infix.length() && infix.charAt(i) >= '0'                               // เงื่อนไขตรวจสอบหลักที่เป็นเลข
                        && infix.charAt(i) <= '9'){
                    tem += infix.charAt(i);
                    i++;                                                                        // post เก็บ tem
                }
                post.push(tem);
            }
            else{
                if(infix.charAt(i) == ')'){                                                     // เงื่อนไชดัก ) 
                    while(!op.isEmpty()&&op.peek() != '('){                                     // เงื่อนไขเก็บ operator จนกว่าจะเจอ (
                        post.push(String.valueOf(op.pop()));                                    
                    }
                    if(!op.isEmpty())op.pop();                                                  //เงื่อนไขให้ op pop อีกหากยังมีอยู่
                    i++;
                }
                else if(!op.isEmpty() && getScore(op.peek()) < 4 
                        && getScore(infix.charAt(i)) < 4 
                        && getScore(infix.charAt(i)) <= getScore(op.peek()))                    //เงื่อนไขตรวจสอบหากลำดับความสำคัญของสัญลักษณ์น้อยกว่าทำการดึงตัวก่อนหน้าของ
                {                                                                               //op มาใส่ใน post
                    post.push(String.valueOf(op.pop()));
                }
                else{
                    op.push(infix.charAt(i));                                                   //op เก็บ operator
                    i++;
                }
            }
        }


        while(!op.isEmpty()){                                                                   //เงื่อนไขลูปเมื่อ infix.length จบก่อนแต่ยังเหลือสมาชิกใน op 
            char cur = op.pop();
            if(getScore(cur) < 4){                                                              //เงื่อนไขเมื่อ score น้อยกว่า 4 ซึ่งเป็น ^ * / + -
                post.push(String.valueOf(cur));                                                 //ทำการเก็บใส่ใน post
            }
        }

        String out = "";                                                                        // out แปลง post stack ให้กลายเป็น String
        while(!post.isEmpty()){
            out = " " + post.pop() + out;
        }
        return out;
    }

    public String findInToPre(String infix){
        infix = reverse(infix);
       Stack<Character> op = new Stack<>();
        Stack<String> post = new Stack<>();
        int i = 0;

        while(i< infix.length()){
            if(infix.charAt(i) >= '0' && infix.charAt(i) <= '9'){
                String tem = "";
                while(i< infix.length() && infix.charAt(i) >= '0' && infix.charAt(i) <= '9'){
                    tem += infix.charAt(i);
                    i++;
                }
                post.push(tem);
            }
            else{
                if(infix.charAt(i) == ')'){
                    while(!op.isEmpty()&&op.peek() != '('){
                        post.push(String.valueOf(op.pop()));
                    }
                    if(!op.isEmpty())op.pop();
                    i++;
                }
                else if(!op.isEmpty() && getScore(op.peek()) < 4 &&getScore(infix.charAt(i)) < 4 && getScore(infix.charAt(i)) < getScore(op.peek())){
                    post.push(String.valueOf(op.pop()));
                }
                else{
                    op.push(infix.charAt(i));
                    i++;
                }
            }
        }


        while(!op.isEmpty()){
            char cur = op.pop();
            if(getScore(cur) < 4){
                post.push(String.valueOf(cur));
            }
        }

        String out = "";
        while(!post.isEmpty()){
            out = " " + post.pop()+ out;
        }
        return reverse(out).trim();
    }
    
    public int callLikeAlu(String s){
        System.out.println("Infix = "+s);
        String postfix = findInToPost(s);
        System.out.println("Postfix = "+postfix);

        String[] splitPost = postfix.split(" ");
        Stack<Integer> stack = new Stack<>();
        System.out.println("\n======================\nProcess:");
        for(String sp : splitPost){
            if(sp.length()>0&&sp.charAt(0) >= '0'&&sp.charAt(0)<='9'){
                System.out.println("Push: "+sp+" in stack");
                stack.push(Integer.parseInt(sp));
            }
            else if(stack.size() > 1){
                int second = stack.pop();
                int first = stack.pop();
                System.out.printf("Pop %d out of stack\n",second);
                System.out.printf("Pop %d out of stack\n",first);
                int out = 0;
                switch (sp) {
                    case "+" -> out = first + second;
                    case "-" -> out = first - second;
                    case "*" -> out = first * second;
                    case "/" -> out = first / second;
                    case "^" -> out = (int)Math.pow(first, second);
                }
                System.out.printf("Calculate: %d %s %d = %d\n",first,sp,second,out);
                System.out.printf("Push: %d in stack\n",out);
                stack.push(out);
            }
        }
        int ans = stack.pop();
        System.out.printf("Pop %d out of stack to be answer\n======================\n\n",ans);
        return ans;
    }

    public String reverse(String s){
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

    public int getScore(char op){          // method ตรวจสอบ op เพื่อส่ง int ที่เป็นลำดับความสำคัญกลับ
        switch (op) {                      // ให้ลำดับความสำคัญจากมากไปน้อย () ^ * / + -
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