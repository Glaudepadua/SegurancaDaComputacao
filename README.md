# Participantes

**Thiago Senhorinha Rose** - 12100774


# Questões

## A

**Por ter diagonal dominante** a sua convergência para métodos iterativos está garantida. Para evitar "saltos" imprecisos em busca da solução, consequência da ausência de uma diagonal fortemente dominante, podemos utilizar fator de relaxão a fim de reduzir o número de passos
necessários para solução do sistema.

## B


### Sub-relaxação

Tanto para **Float** como **Double** possuem como fator de sub-relaxação ideal 0.99. Quando o fator é utilizado na precisão **Float** são
necessárias 4734 iterações contra 4656 para **Double**

<table style="width:100%">
  <tr>
    <th>Precisão</th>
    <th>Fator de Subrelaxação</th>
    <th>Número de iterações</th>
  </tr>
  <tr>
    <td>Float</td>
    <td>0.9900000000000008</td>
    <td>4734</td>
  </tr>
  <tr>
    <td>Double</td>
    <td>0.9900000000000008</td>
    <td>4656</td>
  </tr>
</table>

### Sobre-relaxação
Para **Float** o melhor fator de sobre-relaxação foi 1.99. Este resulta em 105 iterações. Para **Double** o fator foi o mesmo porém foi necessário
855 iterações.

<table style="width:100%">
  <tr>
    <th>Precisão</th>
    <th>Fator de Subrelaxação</th>
    <th>Número de iterações</th>
  </tr>
  <tr>
    <td>Float</td>
    <td>1.9900000000000015</td>
    <td>105</td>
  </tr>
  <tr>
    <td>Double</td>
    <td>1.9900000000000015</td>
    <td>855</td>
  </tr>
</table>

## C

A solução encontrada foi:

```ruby
-285.61706322660547, 435.61706322264723, -411.3756855375413, 
403.8439310309606, -337.3920469335253, 318.55002130951044, 
-249.0675591868268, 229.4134926142211, -159.71344476008989, 
140.00107625052968, -70.28540642272014, 50.56885200040273, 
19.14793945759119, -38.8647944387095, 108.58166645068725, 
-128.2985430362115, 198.015420857575, -217.73229902062886, 
287.44917728594044, -307.16605558947407, 376.88293391415755, 
-396.5998122554685, 466.3166906122128, -486.03356898405383, 
555.7504473708503, -575.4673257724785, 645.1842041887804, 
-664.9010826195394, 734.6179610644258, -754.3348395228095, 
824.0517179930562, -843.7685964699049, 913.4854749346731, 
-933.202353318703, 1002.9192313669328, -1022.6361081287391, 
1092.3529800577273, -1112.0698339200492, 1181.786620327878, 
-1201.5031549649912, 1271.2187499556164, -1290.9308381142337, 
1360.6298385759317, -1380.2799950680264, 1449.7478633661724, 
-1468.5354228471906, 1534.7840352442524, -1541.5571681147792, 
1562.9673299568142, -1402.4010872064707, 799.2921971108461, 
-509.85844089590324, 494.66606234999375, -362.7640606117705, 
389.7821884490916, -269.19045779623934, 299.2391636239229, 
-179.45947388207674, 209.72576540104944, -90.00437755355672, 
120.28629100130185, -0.5690890195002206, 30.852124048354344, 
88.86477742557406, -58.58166189535359, 178.2985418190161, 
-148.0154205349006, 267.7322989376992, -237.44917726729773, 
357.16605558809323, -326.88293391742695, 446.59981225999746, 
-416.3166906170815, 536.0335689890047, -505.7504473758043, 
625.4673257774076, -595.1842041936834, 714.9010826244688, -
684.617961069624, 804.3348395292223, -774.0517180042548, 
893.7685964992558, -863.4854750320995, 983.2023536705574, 
-952.9192326687283, 1072.6361129762006, -1042.3529981382642, 
1162.0699013875976, -1131.7868721107955, 1251.504094622791, 
-1221.2222567980125, 1340.9439258205869, -1310.6786825539878, 
1430.462283269421,  -1400.428172189717, 1521.0743699365112, 
-1494.2595147750633, 1626.9201391463691, -1644.943734550816, 
1944.943734550816]
```

## D

**Erro de Arredondamento:** 0.008054649252031424

## E

**Erro de Truncamento:** 0.06595192666824164

# Algoritmo Utilizado

```java
import java.util.Arrays;

public class GaussSeidel {

    public static void main(String[] args) {

        int numeroDePassosMaximo = 10000;
        double criterioDeParada = 1e-4;
        double fatorDeRelaxacao = 1.1;
        double[] solucaoEsperada = gaussSeidel(fatorDeRelaxacao, numeroDePassosMaximo, 1e-8);
        double[] solucaoDouble = gaussSeidel(fatorDeRelaxacao, numeroDePassosMaximo, criterioDeParada);
        float[] solucaoFloat = gaussSeidel((float) fatorDeRelaxacao, numeroDePassosMaximo, (float) criterioDeParada);

        double erroDeArredondamento = calcularMaximoErro(solucaoFloat, solucaoDouble);
        double erroDeTruncamento = calcularMaximoErro(solucaoDouble, solucaoEsperada);

        System.out.printf("Erro de Arredondamento: %s\n", erroDeArredondamento);
        System.out.printf("Erro de Truncamento: %s\n", erroDeTruncamento);

    }

    private static double[] gaussSeidel(double fatorDeRelaxacao, int numeroDePassosMaximo, double criterioDeParada) {
        int n = 100;
        double[] xInicial = new double[100];
        double[] x = new double[100];
        double diferencia = 1;
        int passo = 1;
        while (passo < numeroDePassosMaximo && diferencia > criterioDeParada) {
            int i = 0;
            x[i] = (1 - fatorDeRelaxacao) * x[i] + fatorDeRelaxacao * (150 - x[i + 1]);
            for (i = 1; i < n / 2; i++) {
                x[i] = (1 - fatorDeRelaxacao) * x[i] + fatorDeRelaxacao * (100 - x[i - 1] - x[i + 1] - x[i + 50]) / 3;
            }
            for (i = n / 2; i < n - 1; i++) {
                x[i] = (1 - fatorDeRelaxacao) * x[i] + fatorDeRelaxacao * (200 - x[i - 50] - x[i - 1] - x[i + 1]) / 3;
            }
            i = n - 1;
            x[i] = (1 - fatorDeRelaxacao) * x[i - 1] + fatorDeRelaxacao * (300 - x[i - 1]);
            passo++;
            diferencia = calcularMaximaDiferencia(xInicial, x);
            xInicial = Arrays.copyOf(x, x.length);
        }
        return x;
    }

    private static float[] gaussSeidel(float fatorDeRelaxacao, int numeroDePassosMaximo, float criterioDeParada) {
        int n = 100;
        float[] xInicial = new float[100];
        float[] x = new float[100];
        float diferencia = 1;
        int passo = 1;
        while (passo < numeroDePassosMaximo && diferencia > criterioDeParada) {
            int i = 0;
            x[i] = (1 - fatorDeRelaxacao) * x[i] + fatorDeRelaxacao * (150 - x[i + 1]);
            for (i = 1; i < n / 2; i++) {
                x[i] = (1 - fatorDeRelaxacao) * x[i] + fatorDeRelaxacao * (100 - x[i - 1] - x[i + 1] - x[i + 50]) / 3;
            }
            for (i = n / 2; i < n - 1; i++) {
                x[i] = (1 - fatorDeRelaxacao) * x[i] + fatorDeRelaxacao * (200 - x[i - 50] - x[i - 1] - x[i + 1]) / 3;
            }
            i = n - 1;
            x[i] = (1 - fatorDeRelaxacao) * x[i - 1] + fatorDeRelaxacao * (300 - x[i - 1]);
            passo++;
            diferencia = calcularMaximaDiferencia(xInicial, x);
            xInicial = Arrays.copyOf(x, x.length);
        }
        return x;
    }

    private static double calcularMaximaDiferencia(double[] xInicial, double[] x) {
        double max = 0;
        double diferencia;
        for (int i = 0; i < x.length; i++) {
            diferencia = Math.abs(xInicial[i] - x[i]);
            if (max < diferencia) {
                max = diferencia;
            }
        }
        return max;
    }

    private static float calcularMaximaDiferencia(float[] xInicial, float[] x) {
        float max = 0;
        float diferencia;
        for (int i = 0; i < x.length; i++) {
            diferencia = Math.abs(xInicial[i] - x[i]);
            if (max < diferencia) {
                max = diferencia;
            }
        }
        return max;
    }

    private static double calcularMaximoErro(double[] solucaoMenosPrecisa, double[] solucaoMaisPrecisa) {
        double erro;
        double erroMaximo = 0;
        for (int i = 0; i < solucaoMenosPrecisa.length; i++) {
            erro = Math.abs((solucaoMenosPrecisa[i] - solucaoMaisPrecisa[i]) / solucaoMaisPrecisa[i]);
            if (erro > erroMaximo) {
                erroMaximo = erro;
            }
        }
        return erroMaximo;
    }

    private static double calcularMaximoErro(float[] solucaoMenosPrecisa, double[] solucaoMaisPrecisa) {
        double erro;
        double erroMaximo = 0;
        for (int i = 0; i < solucaoMenosPrecisa.length; i++) {
            erro = Math.abs((solucaoMenosPrecisa[i] - solucaoMaisPrecisa[i]) / solucaoMaisPrecisa[i]);
            if (erro > erroMaximo) {
                erroMaximo = erro;
            }
        }
        return erroMaximo;
    }

}
```
