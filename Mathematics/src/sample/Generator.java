package sample;

import java.util.Random;

class Generator {
    int result;
    private Random random;

    Generator() {
        random = new Random();
    }

    String arithSchema1() {
        int c;
        int d;
        int a;
        int b;
        do {
            a = random.nextInt(100) + 1;
            b = random.nextInt(100) + 1;
            c = random.nextInt(1000) + 1;
            d = random.nextInt(100) + 2;
            this.result = a + b - c / d;
        } while (c % d != 0 || c == d);
        return a + "+" + b + "-" + c + ":" + d;
    }

    String arithSchema2() {
        int d;
        int e;
        int a;
        int b;
        int c;
        do {
            a = random.nextInt(10) + 2;
            b = random.nextInt(100) + 2;
            c = random.nextInt(100) + 1;
            d = random.nextInt(1000) + 1;
            e = random.nextInt(100) + 2;
            this.result = a * b + c - d / e;
        } while (d % e != 0 || d == e);
        return a + "*" + b + "+" + c + "-" + d + ":" + e;
    }

    String arithSchema3() {
        int a;
        int b;
        int c;
        do {
            a = random.nextInt(1000) + 1;
            b = random.nextInt(100) + 2;
            c = random.nextInt(10) + 2;
            this.result = a / b * c;
        } while (a % b != 0 || a == b);
        return a + ":" + b + "*" + c;
    }

    String arithSchema4() {
        int b;
        int c;
        int a;
        do {
            a = random.nextInt(10) + 2;
            b = random.nextInt(100) + 2;
            c = random.nextInt(100) + 2;
            this.result = a * b / c;
        } while (b % c != 0 || b == c);
        return a + "*" + b + ":" + c;
    }

    String arithSchema5() {
        int a;
        int b;
        int c;
        int d;
        do {
            a = random.nextInt(1000) + 1;
            b = random.nextInt(100) + 2;
            c = random.nextInt(100) + 1;
            d = random.nextInt(100) + 1;
            this.result = a / b + c - d;
        } while (a % b != 0 || a == b);
        return a + ":" + b + "+" + c + "-" + d;
    }

    String arithSchema6() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 1;
        final int c = random.nextInt(100) + 1;
        this.result = a + b - c;
        return a + "+" + b + "-" + c;
    }

    String arithSchema7() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 1;
        final int c = random.nextInt(100) + 1;
        this.result = a - b + c;
        return a + "-" + b + "+" + c;
    }

    String arithSchema8() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 2;
        final int c = random.nextInt(10) + 2;
        final int d = random.nextInt(100) + 1;
        this.result = a + b * c - d;
        return a + "+" + b + "*" + c + "-" + d;
    }

    String arithSchema9() {
        final int a = random.nextInt(10) + 2;
        final int b = random.nextInt(100) + 2;
        final int c = random.nextInt(100) + 1;
        final int d = random.nextInt(100) + 1;
        final int e = random.nextInt(100) + 1;
        this.result = a * b - c + d - e;
        return a + "*" + b + "-" + c + "+" + d + "-" + e;
    }

    String arithSchema10() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 1;
        final int c = random.nextInt(100) + 2;
        final int d = random.nextInt(10) + 2;
        this.result = a + b - c * d;
        return a + "+" + b + "-" + c + "*" + d;
    }

    String arithSchema11() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 1;
        this.result = a + b;
        return a + "+" + b;
    }

    String arithSchema12() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 1;
        this.result = a - b;
        return a + "-" + b;
    }

    String arithSchema13() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(10) + 2;
        this.result = a * b;
        return a + "*" + b;
    }

    String arithSchema14() {
        int a;
        int b;
        do {
            a = random.nextInt(1000) + 1;
            b = random.nextInt(100) + 2;
            this.result = a / b;
        } while (a % b != 0 || a == b);
        return a + ":" + b;
    }

    String equatSchema1() {
        int c;
        int d;
        int a;
        int b;
        int r;
        do {
            a = random.nextInt(100) + 1;
            b = random.nextInt(100) + 1;
            c = random.nextInt(1000) + 1;
            d = random.nextInt(100) + 2;
            r = a + b - c / d;
            this.result = d;
        } while (c % d != 0 || c == d);
        return a + "+" + b + "-" + c + ":x=" + r;
    }

    String equatSchema2() {
        int d;
        int e;
        int a;
        int b;
        int c;
        int r;
        do {
            a = random.nextInt(10) + 2;
            b = random.nextInt(100) + 2;
            c = random.nextInt(100) + 1;
            d = random.nextInt(1000) + 1;
            e = random.nextInt(100) + 2;
            r = a * b + c - d / e;
            this.result = e;
        } while (d % e != 0 || d == e);
        return a + "*" + b + "+" + c + "-" + d + ":x=" + r;
    }

    String equatSchema3() {
        int a;
        int b;
        int r;
        do {
            a = random.nextInt(1000) + 1;
            b = random.nextInt(100) + 2;
            final int c = random.nextInt(10) + 2;
            r = a / b * c;
            this.result = c;
        } while (a % b != 0 || a == b);
        return a + ":" + b + "x=" + r;
    }

    String equatSchema4() {
        int b;
        int c;
        int a;
        int r;
        do {
            a = random.nextInt(10) + 2;
            b = random.nextInt(100) + 2;
            c = random.nextInt(100) + 2;
            r = a * b / c;
            this.result = b;
        } while (b % c != 0 || b == c);
        return a + "x:" + c + "=" + r;
    }

    String equatSchema5() {
        int a;
        int b;
        int c;
        int r;
        do {
            a = random.nextInt(1000) + 1;
            b = random.nextInt(100) + 2;
            c = random.nextInt(100) + 1;
            final int d = random.nextInt(100) + 1;
            r = a / b + c - d;
            this.result = d;
        } while (a % b != 0 || a == b);
        return a + ":" + b + "+" + c + "-x=" + r;
    }

    String equatSchema6() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 1;
        final int c = random.nextInt(100) + 1;
        final int r = a + b - c;
        this.result = b;
        return a + "+x-" + c + "=" + r;
    }

    String equatSchema7() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 1;
        final int c = random.nextInt(100) + 1;
        final int r = a - b + c;
        this.result = c;
        return a + "-" + b + "+x=" + r;
    }

    String equatSchema8() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 2;
        final int c = random.nextInt(10) + 2;
        final int d = random.nextInt(100) + 1;
        final int r = a + b * c - d;
        this.result = c;
        return a + "+" + b + "x-" + d + "=" + r;
    }

    String equatSchema9() {
        final int a = random.nextInt(10) + 2;
        final int b = random.nextInt(100) + 2;
        final int c = random.nextInt(100) + 1;
        final int d = random.nextInt(100) + 1;
        final int e = random.nextInt(100) + 1;
        final int r = a * b - c + d - e;
        this.result = b;
        return a + "x-" + c + "+" + d + "-" + e + "=" + r;
    }

    String equatSchema10() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 1;
        final int c = random.nextInt(100) + 2;
        final int d = random.nextInt(10) + 2;
        final int r = a + b - c * d;
        this.result = d;
        return a + "+" + b + "-" + c + "x=" + r;
    }

    String equatSchema11() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 1;
        final int r = a + b;
        this.result = b;
        return a + "+x=" + r;
    }

    String equatSchema12() {
        final int a = random.nextInt(100) + 1;
        final int b = random.nextInt(100) + 1;
        final int r = a - b;
        this.result = a;
        return "x-" + b + "=" + r;
    }

    String equatSchema13() {
        final int a = random.nextInt(10) + 2;
        final int b = random.nextInt(100) + 1;
        final int r = a * b;
        this.result = b;
        return a + "x=" + r;
    }

    String equatSchema14() {
        int a;
        int b;
        int r;
        do {
            a = random.nextInt(1000) + 1;
            b = random.nextInt(100) + 2;
            r = a / b;
            this.result = b;
        } while (a % b != 0 || a == b);
        return a + ":x=" + r;
    }
}
