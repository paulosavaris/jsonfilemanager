# JSON File Manager

Este projeto é um sistema backend em Java utilizando Spring Boot para gerenciar o recebimento e processamento de arquivos JSON por meio de requisições HTTP. O sistema é capaz de salvar arquivos recebidos em um diretório específico e processá-los sequencialmente, garantindo que cada arquivo seja processado apenas uma vez.

## Funcionalidades

1. **Endpoint de Envio de Arquivos**
    - URL: `http://localhost:8000/send_file`
    - Método HTTP: POST
    - Descrição: Recebe um JSON no corpo da requisição e salva-o como um arquivo no diretório do servidor.
    - O nome do arquivo segue o padrão `YYYY_MM_DD_HH_MM_SS.json`, onde a data e hora representam o momento em que a requisição foi recebida.

2. **Endpoint de Processamento de Arquivos**
    - URL: `http://localhost:8000/get_next_file`
    - Método HTTP: GET
    - Descrição: Processa arquivos JSON recebidos, um por um, garantindo que cada arquivo seja processado apenas uma vez.
    - Lê o próximo arquivo na fila de arquivos a serem processados.
    - Registra em um arquivo de controle o nome dos arquivos já processados.
    - Retorna o conteúdo do próximo arquivo a ser processado.
    - Caso não existam mais arquivos a serem processados, retorna a mensagem "Sem arquivos na fila!".



## Instruções de Uso

### Requisitos
- Java 17 ou superior
- Maven

### Instalação

1. Clone o repositório:

    ```
    git clone https://github.com/paulosavaris/jsonfilemanager.git
    cd jsonfilemanager
    ```

2. Compile e execute a aplicação:

    ```
    mvn spring-boot:run
    ```

### Envio de Arquivos JSON

1. Abra o navegador e acesse `http://localhost:8000/send_file.html`.
2. Insira o conteúdo JSON no campo de texto.
3. Clique em "Enviar" para enviar o arquivo JSON. Uma mensagem de confirmação será exibida.

### Processamento de Arquivos JSON

1. Abra o navegador e acesse `http://localhost:8000/next_file.html`.
2. Clique no botão "Processar próximo arquivo" para processar o próximo arquivo JSON na fila.
3. O conteúdo do próximo arquivo será exibido, juntamente com a lista de arquivos processados e os arquivos restantes.


### Na Pasta Documentacao tem alguns prints de teste da aplicacao, onde é mostrado pasta do json_files vazia, uso do sistema front / web ....
